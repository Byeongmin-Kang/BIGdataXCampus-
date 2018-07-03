import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerManager4 {

	//고객정보를 저장할 변수를 ArrayList로 선언
	static ArrayList<Customer> custList = new ArrayList<>(); 

	//배열은 인덱스를 필요로 함
	static int index = -1;//배열은 0부터 시작하므로 최초 인덱스는 -1이어야 함

	//기본 입력장치로부터 데이터를 입력받기 위해 Scanner객체 생성
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		loadCustomerData();
		
		while(true) {

			System.out.printf("\n[INFO] 고객 수 : %d, 인덱스 : %d\n", custList.size(), index);
			System.out.println("메뉴를 입력하세요.");
			System.out.println("(I)nsert, (P)revious, (N)ext, " +
					"(C)urrent, (U)pdate, (D)elete, (S)ave, (Q)uit");
			System.out.print("메뉴 입력: ");
			String menu = scan.next();
			menu = menu.toLowerCase();  //입력한 문자열을 모두소문자로 변환
			switch(menu.charAt(0)) {
			case 's':
				System.out.println("고객 정보를 저장합니다.");
				saveCustomerData();
				break;
			case 'i':
				System.out.println("고객정보 입력을 시작합니다.");
				insertCustomerData();
				System.out.println("고객정보를 입력했습니다.");
				break;
			case 'p' :
				System.out.println("이전 데이터를 출력합니다.");
				if(index <= 0) {
					System.out.println("이전 데이터가 존재하지 않습니다.");
				}else {
					index--;
					printCustomerData();
				}
			case 'n' :
				System.out.println("다음 데이터를 출력합니다.");
				if(index >= custList.size()-1) {
					System.out.println("다음 데이터가 존재하지 않습니다.");
				}else {
					index++;
					printCustomerData();
				}
				break;
			case 'c' :
				System.out.println("현재 데이터를 출력합니다.");
				if( (index >= 0) && (index < custList.size())) {
					printCustomerData();
				}else {
					System.out.println("출력할 데이터가 선택되지 않았습니다.");
				}
				break;      
			case 'u' :
				System.out.println("데이터를 수정합니다.");
				if( (index >= 0) && (index < custList.size())) {
					System.out.println(index + "번째 데이터를 수정합니다.");
					updateCustomerData();
				}else {
					System.out.println("수정할 데이터가 선택되지 않았습니다.");
				}
				break;
			case 'd' :
				System.out.println("데이터를 삭제합니다.");
				if( (index >= 0) && (index < custList.size())) {
					System.out.println(index + "번째 데이터를 삭제합니다.");
					deleteCustomerData();
				}else {
					System.out.println("삭제할 데이터가 선택되지 않았습니다.");
				}
				break;
			case 'q' :
				System.out.println("프로그램을 종료합니다.");
				scan.close(); //Scanner 객체를 닫아준다.
				System.exit(0); //프로그램을 종료시킨다.
				break;  
			default : 
				System.out.println("메뉴를 잘 못 입력했습니다.");  
			}//end switch
		}//end while
	}//end main

	private static void loadCustomerData() {
		String fileName = "customer.csv";
		FileReader reader = null;
		BufferedReader bufReader = null;
		try {
			reader = new FileReader(fileName);
			bufReader = new BufferedReader(reader); //readLine을 가지고 있어요
			String line = null; //readLine으로 읽은 한 줄을 저장할 변수입니다.
			do {
				line = bufReader.readLine();
				if(line != null) {
					String[] values = line.split(",");
					Customer customer = new Customer();
					customer.setName(values[0]);
					customer.setGender(values[1].charAt(0));
					customer.setEmail(values[2]);
					customer.setBirthYear(Integer.parseInt(values[3]));
					custList.add(customer);
				}
			}while(line != null);
			System.out.println("데이터가 파일에서 로드됐습니다.");
		} catch (IOException e) {
			System.out.println("파일에서 데이터를 불러오는 도중 예외 발생 : " + e.getMessage());
		} finally {
			try { bufReader.close(); } catch(Exception e) {}
			try { reader.close(); } catch(Exception e) {}
		}
	}

	//리스트의 Customer데이터를 CSV파일로 저장
	private static void saveCustomerData() {
		String fileName = "customer.csv";
		FileWriter writer = null;
		BufferedWriter bufWriter = null;
		try {
			writer = new FileWriter(fileName);
			bufWriter = new BufferedWriter(writer);
			for(Customer customer : custList) {
				bufWriter.write(customer.toCSV() + "\r\n");
			}
			bufWriter.flush();
			System.out.println("Customer 데이터가 저장됐습니다.");
		} catch (IOException e) {
			System.out.println("파일 저장 중 예외 발생 : " + e.getMessage());
		} finally {
			try { bufWriter.close(); } catch(Exception e) { }
			try { writer.close(); } catch(Exception e) { }
		}
	}

	public static void insertCustomerData() {
		System.out.print("이름 : ");  
		String name = scan.next();
		System.out.print("성별(M/F) : "); 
		char gender = scan.next().charAt(0);
		System.out.print("이메일 : "); 
		String email = scan.next();
		System.out.print("출생년도 : ");  
		int birthYear = scan.nextInt();

		//고객 객체를 ArrayList에 저장
		Customer customer = new Customer();
		customer.setName(name);
		customer.setGender(gender);
		customer.setEmail(email);
		customer.setBirthYear(birthYear);
		custList.add(customer);
	}

	//고객데이터 출력
	public static void printCustomerData() {
		System.out.println("==========CUSTOMER INFO================");
		//    System.out.println(custList[index].toString());
		Customer customer = custList.get(index);
		System.out.println("이름 : " + customer.getName());
		System.out.println("성별 : " + customer.getGender());
		System.out.println("이메일 : " + customer.getEmail());
		System.out.println("출생년도 : " + customer.getBirthYear());
		System.out.println("=======================================");
	}

	//index 위치의 고객정보를 삭제합니다.
	public static void deleteCustomerData() {
		custList.remove(index);
	}

	//index 위치의 고객 정보를 수정합니다.
	public static void updateCustomerData() {
		Customer customer = custList.get(index);
		System.out.println("---------UPDATE CUSTOMER INFO----------");
		System.out.print("이름(" + customer.getName() + ") :");
		customer.setName(scan.next());

		System.out.print("성별(" + customer.getGender() + ") :");
		customer.setGender(scan.next().charAt(0));

		System.out.print("이메일(" + customer.getEmail() + ") :");
		customer.setEmail(scan.next());

		System.out.print("출생년도(" + customer.getBirthYear() + ") :");
		customer.setBirthYear(scan.nextInt());    
	}

}//end class