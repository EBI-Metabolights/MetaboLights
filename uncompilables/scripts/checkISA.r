library(Risa)
setwd("/ebi/ftp/pub/databases/metabolights/studies/public")
studies <- lapply(dirname(list.files(pattern="^i_.*.txt$", full.name=TRUE, recursive=TRUE)), function(x) readISAtab(path=x))
