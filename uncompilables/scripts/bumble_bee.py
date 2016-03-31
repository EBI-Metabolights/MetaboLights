#!/usr/bin/env python

"""A simple python script template.
"""

import os
import sys
import argparse
import glob
import json
import zipfile

class readable_dir(argparse.Action):
    def __call__(self,parser, namespace, values, option_string=None):
        prospective_dir=values
        if not os.path.isdir(prospective_dir):
            raise argparse.ArgumentTypeError("readable_dir:{0} is not a valid path".format(prospective_dir))
        if os.access(prospective_dir, os.R_OK):
            setattr(namespace,self.dest,prospective_dir)
        else:
            raise argparse.ArgumentTypeError("readable_dir:{0} is not a readable dir".format(prospective_dir))

def path_to_dict(path):
    d = {'name': os.path.basename(path)}
    filename, file_extension = os.path.splitext(path)
    if os.path.isdir(path):
        d['type'] = "directory"
        d['children'] = [path_to_dict(os.path.join(path,x)) for x in os.listdir(path)]
    elif file_extension == ".zip":
        d['type'] = "directory"
        d['children'] = read_zip_file(path, os.path.basename(path))
    else:
        d['type'] = "file"
    return d

def read_zip_file(filepath, filename):
    zfile = zipfile.ZipFile(filepath)
    root = os.path.splitext(filename)[0] + "/"
    zipFilesList = []
    for finfo in zfile.infolist():
        zipFilesList.append(finfo.filename)
    return getTree(root,zipFilesList, [])[0]

def getTree(folderName, filesList, scannedList):
    el = []
    for file in filesList:
        if folderName in file:
            if file != folderName and file not in scannedList:
                e = {'name': file}
                if file.endswith('/'):
                    e['type'] = "directory"
                    temparr = getTree(file, filesList, scannedList)
                    e['children'] = temparr[0]
                    scannedList = scannedList + temparr[1]
                    scannedList.append(file)
                    el.append(e)
                else:
                    e['type'] = "file"
                    scannedList.append(file)
                    el.append(e)
    return [ el , scannedList ]

def main(arguments):
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument('-l', '--launch_directory', action=readable_dir, default = "/net/isilonP/public/rw/homes/tc_cm01/metabolights/prod/studies/stage/private/" )
    parser.add_argument('-o', '--outfile', help="Output file", default="/homes/venkata/bumble_bee_results/metabolights.json", type=argparse.FileType('w'))
    args = parser.parse_args(arguments)
    json.dump(path_to_dict(args.launch_directory), args.outfile)

if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))
