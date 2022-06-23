package diplom.gorchanyuk.project.diplom.config;

import com.ibm.icu.text.Transliterator;

public class Transcriptor {

    private static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    public static String transcript(String str){
//        Преобразует текст из кирилицы в латиницу,
//        заменяя пробелы на подчеркивание и переводя текст в нижний регистр
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(str.replace(' ', '_').toLowerCase());
    }
}
