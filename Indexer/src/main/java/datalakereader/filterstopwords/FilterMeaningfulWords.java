package datalakereader.filterstopwords;

import java.util.Set;

public class FilterMeaningfulWords {

    private final String language;

    public FilterMeaningfulWords(String language) {
        this.language = language;
    }

    public Set<String> selectLanguage() {
        return switch (language) {
            case "Spanish" -> MeaningfulWords.stopwords_es;
            case "Italian" -> MeaningfulWords.stopwords_it;
            case "Galician" -> MeaningfulWords.stopwords_gl;
            case "Arabic" -> MeaningfulWords.stopwords_ar;
            case "Catalan" -> MeaningfulWords.stopwords_ca;
            case "Greek" -> MeaningfulWords.stopwords_el;
            case "Russian" -> MeaningfulWords.stopwords_ru;
            case "Portuguese" -> MeaningfulWords.stopwords_pt;
            case "German" -> MeaningfulWords.stopwords_de;
            case "French" -> MeaningfulWords.stopwords_fr;
            case "Hebrew" -> MeaningfulWords.stopwords_he;
            case "Korean" -> MeaningfulWords.stopwords_ko;
            case "Polish" -> MeaningfulWords.stopwords_pl;
            default -> MeaningfulWords.stopwords_in;
        };
    }

    public Boolean isMeaningfulWord(String word) {
        Set<String> stopWords = selectLanguage();
        if (stopWords.contains(word)) {
            return false;
        }
        return word.length() > 2;
    }
}
