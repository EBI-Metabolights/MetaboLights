library(metaboanalyst)
getwd()
InitDataObjects("specbin", "stat", FALSE);
Read.CSVdata("/homes/kalai/rcloud-test/nmr_bins.csv", "rowu", 'disc');
SanityCheckData();
ReplaceMin()
Normalization('NULL','NULL','NULL','NULL')
PlotNormSummary("/homes/kalai/rcloud-test/norm_0_0)", "png", 72, width=NA);