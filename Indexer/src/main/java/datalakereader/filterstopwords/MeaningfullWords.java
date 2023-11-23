package datalakereader.filterstopwords;

import java.util.HashSet;
import java.util.Set;

public interface MeaningfullWords {

    Set<String> stopwords_in = new HashSet<>(Set.of(
            "the", "some", "any", "aux","all", "many", "few", "several",
            "both", "neither", "either", "each", "every", "this", "that",
            "your", "his", "her", "its", "our", "their",
            "those", "these", "whose", "half", "much", "little",
            "enough", "plenty", "most", "more", "another", "other", "others", "one", "two",
            "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "what", "rather", "such", "quite", "when", "where",
            "which", "who", "she", "you", "they", "him","about", "above", "across",
            "after", "against", "along", "amid", "among",
            "around", "before", "behind", "below", "beneath", "beside", "between",
            "beyond", "but", "concerning", "considering", "despite", "down",
            "during", "except", "for", "from", "inside", "into", "like", "near",
            "off", "onto", "out", "outside", "over", "past", "regarding", "round",
            "since", "through", "throughout", "toward", "under", "underneath",
            "until", "unto", "upon", "with", "within", "without", "close", "front"
    ));
    Set<String> stopwords_es = new HashSet<>(Set.of(
            "a", "ante", "bajo", "con", "contra", "de", "desde", "durante", "en", "entre",
            "hacia", "hasta", "mediante", "para", "por", "según", "sin", "sobre", "tras", "versus", "vía",
            "el", "la", "lo", "los", "las", "un", "una", "unos", "unas", "este", "esta", "estos", "estas",
            "ese", "esa", "esos", "esas", "aquel", "aquella", "aquellos", "aquellas","mi", "tu", "su",
            "nuestro", "vuestro"
    ));

    Set<String> stopwords_it = new HashSet<>(Set.of(
            "a", "con", "da", "di", "in", "su", "per", "tra", "fra",
            "oltre", "verso", "sotto", "sopra", "vicino", "lontano", "presso", "fuori",
            "dentro", "fuori da", "davanti a", "dietro", "dopo", "prima di", "durante",
            "contro", "secondo", "in base a", "a causa di","il", "lo", "la", "i", "gli", "le",
            "un", "uno", "una", "dei", "degli", "delle", "questo", "questa", "questi", "queste",
            "quello", "quella", "quelli", "quelle"
    ));

    Set<String> stopwords_fr = new HashSet<>(Set.of(
            "le", "la", "les", "un", "une", "des", "ce", "cet", "cette", "ces",
            "à", "après", "avant", "avec", "chez", "contre", "dans", "de", "depuis",
            "devant", "en", "entre", "jusque", "hors", "pour", "sans", "sous", "sur",
            "vers", "pendant", "comme", "d'abord", "alors", "maintenant",
            "là", "tout", "très", "bien", "mal", "encore", "toujours", "jamais",
            "déjà", "presque", "beaucoup", "peu", "plusieurs", "chaque", "autre",
            "même", "tel", "certain", "plus", "moins", "aussi",
            "autant", "tant", "trop", "quelque", "quel", "qui", "que", "dont", "où"
    ));

    Set<String> stopwords_de = new HashSet<>(Set.of(
            "der", "die", "das", "ein", "eine", "einer", "einem", "einen",
            "in", "aus", "nach", "vorbei", "bis",
            "mit", "bei", "von", "zu", "zwischen", "gegenüber", "an", "auf",
            "hinter", "vor", "neben", "über", "unter", "durch", "für", "gegen",
            "ohne", "um", "während", "nachdem", "trotz", "anstatt", "wegen", "seit"
    ));

    Set<String> stopwords_pt = new HashSet<>(Set.of(
            "o", "a", "os", "as", "um", "uma", "uns", "umas",
            "de", "da", "do", "das", "dos", "com", "em", "por", "para", "até", "ante",
            "depois", "embaixo", "sobre", "dentro", "fora", "sem",
            "sob", "contra", "através", "além", "entre", "ao", "aonde", "em cima de",
            "ao redor de", "antes de", "depois de", "durante", "por causa de", "graças a"
    ));
    Set<String> stopwords_ar = new HashSet<>(Set.of(
            "ال", "في", "من", "عن", "مع", "ل", "لكن", "ب", "ها", "و", "أو", "ثم", "كيف",
            "أن", "لماذا", "متى", "أين",
            "كان", "ليس", "عليه", "عليها", "له", "لها", "لديه", "لديها", "هو", "هي",
            "نحن", "أنت", "أنتِ", "أنتم", "أنتن", "هم", "هن", "إن", "منذ", "قبل",
            "بعد", "خلال", "إلى", "على", "حول", "فوق", "تحت", "بين", "أمام", "وراء",
            "جميع", "كل", "بعض", "البعض", "هذا", "هذه", "هؤلاء", "هاتين", "هذين",
            "هنالك", "هناك", "الآن", "أينما", "كيفما", "أيضا", "أكثر", "قليلا", "كثيرا"
    ));

    Set<String> stopwords_ca = new HashSet<>(Set.of(
            "el", "la", "els", "les", "un", "una", "uns", "unes",
            "de", "en", "per", "amb", "sense", "sota", "sobre", "davant",
            "darrere", "entre", "fins", "després", "abans", "durant", "mitjançant",
            "a", "cap", "contra", "vers", "a través de", "més enllà de", "com", "quan",
            "on", "com si", "encara que", "perquè", "si", "no", "ara", "també",
            "molt", "bé", "malament", "encara", "sempre", "mai", "ja", "casi",
            "massa", "poc", "varis", "cadascun", "altre", "mig", "tot", "molts", "alguns"
    ));
    Set<String> stopwords_el = new HashSet<>(Set.of(
            "ο", "η", "το", "οι", "τα",
            "ενας", "ενα", "μια", "σε", "με", "για",
            "υπο", "πανω", "κατω", "μεσα", "επανω", "πισω", "μεταξυ", "πριν", "μετα",
            "αναμεσα", "προ", "απο", "αντι", "προς", "δια", "επι", "εξω",
            "πανω απο", "περισσοτερο", "καλα", "κακα", "ακομα", "παντα", "ποτε",
            "ηδη", "σχεδον", "παρα", "λιγο", "καποιος", "καποια", "αλλος", "μισο", "ολο",
            "πολλοι", "καποιοι"
    ));
    Set<String> stopwords_ru = new HashSet<>(Set.of(
            "и", "в", "во", "не", "что", "он", "на", "я", "с", "со",
            "как", "а", "то", "все", "она", "так", "его", "но", "да",
            "ты", "к", "у", "же", "вы", "за", "бы", "по", "только", "ее",
            "мне", "было", "вот", "от", "меня", "еще", "нет", "о", "из",
            "ему", "теперь", "когда", "даже", "ну", "вдруг", "ли", "если",
            "уже", "или", "ни", "быть", "был", "него", "до", "вас", "нибудь",
            "опять", "уж", "вам", "ведь", "там", "потом", "себя", "ничего",
            "ей", "может", "они", "тут", "где", "есть", "надо", "ней", "для"
    ));
    Set<String> stopwords_gl = new HashSet<>(Set.of(
            "o", "a", "os", "as", "un", "unha", "uns", "unhas", "co", "coa", "cos", "coas",
            "en", "no", "na", "nos", "nas", "pola", "polo", "polas"
            , "dun", "dunha", "duns", "dunhas", "de", "do", "da", "dos", "das",
            "con", "sen", "por", "para", "porque", "cando", "onde", "como", "pero", "mais",
            "tamén", "xa", "aínda", "agora", "nunca", "sempre", "ao", "ós", "ás", "aos"
    ));
    Set<String> stopwords_he = new HashSet<>(Set.of(
            "את", "על", "עם", "ל", "ב", "כש", "כן", "כך", "או", "אם", "כמו",
            "הוא", "היא", "אנחנו", "אתם", "הם", "הן", "זה", "זו", "לכם", "להם", "מתחת", "מעל", "בין", "לבין", "עלי", "מעלי", "מאחורי",
            "לפני", "אחרי", "אצל", "אל", "לעיכן", "ליד", "מול", "למול", "באמצע"
    ));

    Set<String> stopwords_ko = new HashSet<>(Set.of(
            "그리고", "그래서", "그러므로", "그런데", "그리하여", "그러한데", "또한", "또는", "반면에",
            "이지만", "하지만", "그렇지만", "그러나", "그래도", "그리고도", "비록", "비록...지만", "아니면", "혹은"
    ));
    Set<String> stopwords_pl = new HashSet<>(Set.of(
            "i", "w", "na", "do", "za", "przed", "po", "pod", "nad", "bez",
            "przez", "obok", "zamiast", "między", "u", "około", "przy", "od", "o", "a",
            "ale", "lub", "czy", "niż", "jak", "ponieważ", "bo", "aby", "żeby", "że"
    ));
}



