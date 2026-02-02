package data;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import androidx.test.espresso.DataInteraction;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.iteco.fmhandroid.R;

public class Row {

    public final static String ValidLogin = "login2";
    public final static String ValidPassword = "password2";
    public final static String InValidLogin = "login222";
    public final static String InValidPassword = "password222";
    public final static String EmptyLogin = " ";
    public final static String EmptyPassword = " ";
    public final static String UpperCaseLogin = "LOGIN2";
    public final static String UpperCasePassword = "PASSWORD2";

    public final static String SqlInjectionLogin = "admin' OR '1'='1";
    public final static String SqlInjectionPassword = "pass' OR 'x'='x";
    public final static String InvalidCategory = "%%%";
    public final static String InvalidTitle = "!!!";
    public final static String InvalidDescription = "???";
    public final static String EmptyCategory = " ";
    public final static String EmptyTitle = " ";
    public final static String EmptyDescription = " ";
    public final static String EmptyTime = " ";
    public final static String InvalidsCategory = "TEST";


    private static final Faker faker = new Faker(new Locale("ru"));

    public static String getRandomCategory() {
        return Category[(int) (Math.random() * Category.length)];
    }

    public final static String[] Category = {
            "Объявление",
            "День рождения",
            "Зарплата",
            "Профсоюз",
            "Праздник",
            "Массаж",
            "Благодарность",
            "Нужна помощь"
    };

    public static String getRandomTitle() {
        return Title[(int) (Math.random() * Category.length)];
    }

    public final static String[] Title = {
            "Продам щебень",
            "Продам песок",
            "Продам ДТ",
            "Продажа домов под ключ",
            "Продажа участков для строительства",
            "Продам гараж",
            "Продам дачу",
            "Продам квартиру",
            "Сдам офис в аренду",
            "Продам экскаватор",
            "Куплю металлолом",
            "Услуги земляных работ",
            "Монтаж канализации",
            "Ремонт кровли",
            "Установка кондиционеров",
            "Электромонтажные работы",
            "Автомойка самообслуживания",
            "Шиномонтаж 24/7",
            "Грузоперевозки по России",
            "Строительство бань и саун",
            "Ландшафтный дизайн",
            "Озеленение территорий",
            "Уборка строительного мусора",
            "Изготовление металлоконструкций",
            "Ремонт спецтехники",
            "Аренда автокрана",
            "Продам б/у кирпич",
            "Теплоизоляция труб",
            "Монтаж водоснабжения",
            "Сантехнические услуги",
            "Капитальный ремонт квартир"
    };

    public static String getRandomDescription() {
        return "Номер телефона: " + faker.phoneNumber().phoneNumber();
    }


    public static String getTodayDate() {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
    }

    public static String getPlus2DayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.getTime());
    }

    public static String getPlus30DayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.getTime());
    }

    public static String getMinus30DayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendar.getTime());
    }

    public static String getTime() {
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
    }

    public static String getFirstTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime());
    }

    public static String getTimePlus2Hours() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, +2);
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime());
    }

}





