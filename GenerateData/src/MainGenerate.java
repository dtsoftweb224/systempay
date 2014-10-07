import java.util.List;

/* Класс для генерации данных
 * в заявки в БД
 */
public class MainGenerate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String list = null;
		list = GenerateValuesZayvki.genValue();
		System.out.println("Start generate");
		try {
			ZayvkiDB.InsertZayvki(list);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		System.out.println("End generate");
	}

}
