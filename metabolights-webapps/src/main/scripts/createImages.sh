#/bin/bash

# This script is to generate png's of raw MS files using Risa

PATHTOSTUDY="/ebi/ftp/pub/databases/metabolights/studies/public/$1"
OUTDIR="/ebi/ftp/pub/databases/metabolights/derived/public/$1/"

mkdir -p $OUTDIR

R --vanilla <<EOF

#retrieves a list of the raw data files per assay file from the ISAtab object, with full path
getRawDataFiles = function(isa){
  msfiles <- lapply(isa["data.filenames"], function(x) x[Risa:::isatab.syntax\$raw.spectral.data.file])
  #msfiles is a list with one element per assay file, and each element is a list with the 'Raw Spectral Data File's
  msfiles <- sapply(msfiles, function(x) sapply(x, function(y) paste(isa["path"], y, sep=.Platform\$file.sep)))
  return(msfiles)
}

library(Risa)
library(xcms)

study <- readISAtab(path="$PATHTOSTUDY")

msfiles <- getRawDataFiles(study)

dummy <- sapply(msfiles, function(x) {png(paste("$OUTDIR", basename(x),"-raw.png", sep="")) ;
                                     xr <- xcmsRaw(x); plotRaw(xr, log=TRUE);
                                     dev.off()})
dummy <- sapply(msfiles, function(x) {png(paste("$OUTDIR", basename(x),"-tic.png", sep="")) ;
                                     xr <- xcmsRaw(x);
                                     plotTIC(xr)})

EOF