package datalakereader;

import java.util.Set;

public class FilterMeaningfullWords {

    private String storedLanguage;

    public void storeLanguage(String language) {
        storedLanguage = language;
    }

    public Set<String> selectLanguage() {
        Set<String> stopwords;
        switch (storedLanguage) {
            case "Spanish":
                stopwords = MeaningfullWords.stopwords_es;
                break;
            case "Italian":
                stopwords = MeaningfullWords.stopwords_it;
                break;
            case "Galician":
                stopwords = MeaningfullWords.stopwords_gl;
                break;
            case "Arabic":
                stopwords = MeaningfullWords.stopwords_ar;
                break;
            case "Catalan":
                stopwords = MeaningfullWords.stopwords_ca;
                break;
            case "Greek":
                stopwords = MeaningfullWords.stopwords_el;
                break;
            case "Russian":
                stopwords = MeaningfullWords.stopwords_ru;
                break;
            case "Portuguese":
                stopwords = MeaningfullWords.stopwords_pt;
                break;
            case "German":
                stopwords = MeaningfullWords.stopwords_de;
                break;
            case "French":
                stopwords = MeaningfullWords.stopwords_fr;
                break;
            case "Hebrew":
                stopwords = MeaningfullWords.stopwords_he;
                break;
            case "Korean":
                stopwords = MeaningfullWords.stopwords_ko;
                break;
            case "Polish":
                stopwords = MeaningfullWords.stopwords_pl;
                break;
            default:
                stopwords = MeaningfullWords.stopwords_in;
                break;
        }
        return stopwords;
    }

    public Boolean isMeaningfulWord (String word){
        Set<String> stopwords = selectLanguage();
        if (stopwords.contains(word)) {
            return false;
        }
        return word.length() > 2;
    }
}
