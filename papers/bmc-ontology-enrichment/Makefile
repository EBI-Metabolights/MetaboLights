all: bmc_article.pdf

bmc_article.bbl: bmc_article.bib
	pdflatex bmc_article || true
	bibtex bmc_article || true

bmc_article.pdf: bmc_article.tex bmc_article.bbl
	pdflatex bmc_article.tex
	pdflatex bmc_article.tex
	pdflatex bmc_article.tex

distclean: clean

clean:
	rm -f *.bbl *.aux art.pdf bmc_article.pdf *.blg *.log *.ps *.fff *.lof *.lot *.ttt *.dvi *~ *.Rout
