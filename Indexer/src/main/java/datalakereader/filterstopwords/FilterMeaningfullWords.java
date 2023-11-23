package datalakereader.filterstopwords;

import java.util.Set;

public class FilterMeaningfullWords {

    private String storedLanguage;

    public void storeLanguage(String language) {
        storedLanguage = language;
    }

    public Set<String> selectLanguage() {
        return switch (storedLanguage) {
            case "Spanish" -> MeaningfullWords.stopwords_es;
            case "Italian" -> MeaningfullWords.stopwords_it;
            case "Galician" -> MeaningfullWords.stopwords_gl;
            case "Arabic" -> MeaningfullWords.stopwords_ar;
            case "Catalan" -> MeaningfullWords.stopwords_ca;
            case "Greek" -> MeaningfullWords.stopwords_el;
            case "Russian" -> MeaningfullWords.stopwords_ru;
            case "Portuguese" -> MeaningfullWords.stopwords_pt;
            case "German" -> MeaningfullWords.stopwords_de;
            case "French" -> MeaningfullWords.stopwords_fr;
            case "Hebrew" -> MeaningfullWords.stopwords_he;
            case "Korean" -> MeaningfullWords.stopwords_ko;
            case "Polish" -> MeaningfullWords.stopwords_pl;
            default -> MeaningfullWords.stopwords_in;
        };
    }

    public Boolean isMeaningfulWord (String word){
        Set<String> stopwords = selectLanguage();
        if (stopwords.contains(word)) {
            return false;
        }
        return word.length() > 2;
    }
}
