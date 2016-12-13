cd $HOME/metabolights/scripts
./calc_StudySize_oracle.sh
source /homes/oracle/ora11setup.sh
sqlplus xx/xx@mtadev @ml_stats2.sql
sqlplus xx/xx@mtapro @ml_stats2.sql
sqlplus xx/xx@mtatst @ml_stats2.sql
