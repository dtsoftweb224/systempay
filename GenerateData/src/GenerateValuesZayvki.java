import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/* Класс для генерации данных 
 * для создания заявок 
 */
public class GenerateValuesZayvki {
	
	static final int masLen = 10;
	static final int outMasLen = 100;
	/* Массив для генерации имени */
	static final String nameVariant[] = {"Иван","Сергей","Петр","Максим",
		"Дмитрий","Олег","Влад","Алексей","Роман","Игорь","Денис"}; 
	
	/* Массив для генерации фамилии */
	static final String famVariant[] = {"Иванов","Сергеев","Петров","Максимов",
		"Дмитриев","Широков","Сидоров","Калашников","Попов","Сухов","Денисов"};
	
	/* Массив для генерации отчеств */
	static final String otchVariant[] = {"Иванович","Сергеевич","Петрович",
		"Максимович","Дмитриевич","Олегович","Владимирович","Алексевич",
		"Романович","Игоревич","Денисович"};
	
	static final String status = "Новая";
	static final String payIn = "WebMoney";
	static final String payOut = "УбРИР";
	static final String type = "На карту";
	static final String mail = "1mail@mail.ru";	
	
	/* Функция генерации даных */
	@SuppressWarnings("null")
	public static String genValue() {
		
		//List<String> values = null;
		Random r = new Random();
		// Получение текущей даты в текстовом формате
		Date dt = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(dt);
		int a = 0;		
		String result = "";
		
		for (int i = 0; i < outMasLen; i++) {
			
			String str = "";
			str = "('";
			//payOut
			str = str + payOut + "','";
			//date
			str = str + date + "',";
			//kommis
			str = str + r.nextDouble() + ",'";
			//numberPay
			str = str + generateNumberPay() + "','";
			//status
			str = str + status + "',";
			//summaCard
			str = str + r.nextDouble() + ",";
			//summaPay
			str = str + r.nextDouble() + ",'";
			//payIn
			str = str + payIn + "','";
			//valuta
			str = str + "RUR" + "','";
			//wmid
			str = str + "235346454" + "','";
			//mail
			str = str + mail + "','";
			//numSchet
			str = str + "845745345345" + "','";
			//fName
			a = r.nextInt(10);
			str = str + nameVariant[a] + "','";
			//lName
			a = r.nextInt(10);
			str = str + famVariant[a] + "','";
			//otch
			a = r.nextInt(10);
			str = str + otchVariant[a] + "','";
			//type
			str = str + type + "')";
			// Добавление строки в список
			if (i < 1) {
				result = result + str;
			} else {
				result = result + "," + str;
			}
		}
		
		return result; 
	}
	
	/* Функция генерации номера платежа */
	public static String generateNumberPay() {
		
		String numPay = "";
		int num = 1;
		
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("MMddyy");
		numPay = df.format(now);
		
		//if (type.getValue().equals("На карту")) {
			numPay = numPay + "C";
			try {
				num = DB.GetNumIndexPay(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			numPay = numPay + String.valueOf(num);
		//}
		
		return numPay;
	}
}
