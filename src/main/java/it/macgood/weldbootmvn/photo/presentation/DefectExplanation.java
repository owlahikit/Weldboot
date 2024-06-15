package it.macgood.weldbootmvn.photo.presentation;

import it.macgood.weldbootmvn.stats.model.ModelClass;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefectExplanation {
    private String photoUrl;
    private List<String> detected;
    private List<String> reasons;
    private List<String> removal;

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setReasons(List<ModelClass> classes) {
        String s = getString(classes);

        var description = new ArrayList<String>();
        if (s.contains("ADJ")) {
            description.add("\n- Высокий сварочный ток");
            description.add("\n- Слишком низкое напряжение");
        }
        if (s.contains("GEO")) {
            description.add("\n- Диаметр электрода не соответствует толщине свариваемого материала");
            description.add("\n- Cлишком высокая скорость сварки");
            description.add("\n- Неправильный присадочный металл");
        }
        if (s.contains("INT")) {
            description.add("\n- Плохая зачистка свариваемых поверхностей");
            description.add("\n- Высокая скорость сварки");
        }
        if (s.contains("NON")) {
            description.add("\n- Между свариваемым металлом было слишком много места");
            description.add("\n- Низкие настройки силы тока, которого недостаточно");
            description.add("\n- Необходим больший диаметр электрода");
        }
        if (s.contains("PRO")) {
            description.add("\n - Неправильная подготовка кромок.");
            description.add("\n- Неправильный выбор сварочного тока и режима сварки.");
        }
        this.reasons = description;
    }

    public void setRemoval(List<ModelClass> classes) {
        String s = getString(classes);

        var removal = new ArrayList<String>();
        if (s.contains("ADJ")) {

            removal.add("\n - Зачистить шлифовальным инструментом поверхность сварного соединения от брызг металла.");
        }
        if (s.contains("GEO")) {
            removal.add("\n - Для непровара корень шва в месте дефекта вычищают и заваривают повторно.");
            removal.add("\n - Для подреза зачистить и завариварить шов заново.");
        }
        if (s.contains("INT")) {
            removal.add("\n - Дефектный участок шлака, пор и прожога удалить с помощью шлифовального инструмента и заварить вновь.");
        }
        if (s.contains("NON")) {
            removal.add("\n - Зачистка незаваренных участков и повторное наложение шва для несплавлений и незаполнения раковины");
        }
        if (s.contains("PRO")) {
            removal.add("\n - Зачистка поверхности с помощью напильика или шлифовальной бумаги в местах заусенцев, задир и забоин");
        }
        this.removal = removal;
    }

    public void setDetected(List<ModelClass> classes) {
        String s = getString(classes);

        var reasons = new ArrayList<String>();
        if (s.contains("ADJ")) {
            reasons.add("\n - брызги, прожоги от дуги");
        }
        if (s.contains("GEO")) {
            reasons.add("\n - подрез, непровар, наплыв, чешуйчатость, западание, неравномерность");
        }
        if (s.contains("INT")) {
            reasons.add("\n - кратер, шлак, свищ, пора, прожог или включения");
        }
        if (s.contains("NON")) {
            reasons.add("\n - незаполнение раковины или несплавление");
        }
        if (s.contains("PRO")) {
            reasons.add("\n - заусенец, торец, задир или забоина");
        }
        this.detected = reasons;
    }

    private static String getString(List<ModelClass> classes) {
        var buffer = new StringBuffer();
        Set<ModelClass> unique = new HashSet<>(classes);
        for (ModelClass modelClass : unique) {
            switch (modelClass) {
                case ADJ:
                    buffer.append("ADJ ");
                    break;
                case GEO:
                    buffer.append("GEO ");
                    break;
                case INT:
                    buffer.append("INT ");
                    break;
                case NON:
                    buffer.append("NON ");
                    break;
                case PRO:
                    buffer.append("PRO ");
                    break;
            }
        }
        String s = buffer.toString();
        return s;
    }


}
