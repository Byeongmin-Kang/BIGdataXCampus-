import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class CustomerManager3 {

	//고객 정보를 저장할 자료구조 선언
	static ArrayList<Customer> custList = new ArrayList<>();

	//리스트 정보를 조회하기 위해 인덱스를 필요로 함
	static int index = -1;

	static int count = 0;//custList.size()

	//기본 입력장치로부터 데이터를 입력받기 위해 Scanner객체 생성
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		while(true) {
			count = custList.size();
			System.out.printf("\n[INFO] 고객 수 : %d, 인덱스 : %d\n", count, index);
			System.out.println("메뉴를 입력하세요.");
			System.out.println("(I)nsert, (P)revious, (N)ext, " +
					"(C)urrent, (U)pdate, (D)elete, (Q)uit");
			System.out.print("메뉴 입력: ");
			String menu = scan.next();
			menu = menu.toLowerCase();	//입력한 문자열을 모두소문자로 변환
			switch(menu.charAt(0)) {
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
					printCustomerData(index);
				}
				break;
			case 'n' :
				System.out.println("다음 데이터를 출력합니다.");
				if(index >= count-1) {
					System.out.println("다음 데이터가 존재하지 않습니다.");
				}else {
					index++;
					printCustomerData(index);
				}
				break;
			case 'c' :
				System.out.println("현재 데이터를 출력합니다.");
				if( (index >= 0) && (index < count)) {
					printCustomerData(index);
				}else {
					System.out.println("출력할 데이터가 선택되지 않았습니다.");
				}
				break;			
			case 'u' :
				System.out.println("데이터를 수정합니다.");
				if( (index >= 0) && (index < count)) {
					System.out.println(index + "번째 데이터를 수정합니다.");
					updateCustomerData(index);
				}else {
					System.out.println("수정할 데이터가 선택되지 않았습니다.");
				}
				break;
			case 'd' :
				System.out.println("데이터를 삭제합니다.");
				if( (index >= 0) && (index < count)) {
					System.out.println(index + "번째 데이터를 삭제합니다.");
					deleteCustomerData(index);
				}else {
					System.out.println("삭제할 데이터가 선택되지 않았습니다.");
				}
				break;
	         case 'q' :
	             System.out.println("프로그램을 종료합니다.");
	             scan.close();   //Scanner 객체를 닫아준다.
	             
	             if(count > 0) {
	                fileSaveCustomerData();
	                System.out.println("asdf");
	             }
	             
	             System.exit(0);   //프로그램을 종료시킨다.
	             break;   
			default : 
				System.out.println("메뉴를 잘 못 입력했습니다.");	
			}//end switch
		}//end while
	}//end main

	   public static void insertCustomerData() {
		      
		      System.out.print("이름 : ");   
		      String name = scan.next();
		      
		      char gender;
		      while(true) {
		         System.out.print("성별(M/F) : ");   
		         gender = scan.next().charAt(0);
		         String genderPatternString = "[MFmf]";
		         Pattern genderPattern = Pattern.compile(genderPatternString);
		         Matcher genderMatcher = genderPattern.matcher(Character.toString(gender));
		         if(genderMatcher.find()) {
		            break;
		         }
		         else {
		            System.out.print("M과 F 중 하나만 입력하세요.\n");   
		         }
		      }
		      
		      String email;
		      while(true) {
		         System.out.print("이메일 : ");   
		         email = scan.next();
		         String emailPatternString = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
		         Pattern emailPattern = Pattern.compile(emailPatternString);
		         Matcher emailMatcher = emailPattern.matcher(email);
		         if(emailMatcher.find()) {
		            break;
		         }
		         else {
		            System.out.print("@와 도메인이 포함된 정확한 형식의 이메일을 입력해주세요.\n");   
		         }
		      }
		      
		      int birthYear;
		      while(true) {
		         System.out.print("출생년도(4자리) : ");   
		         birthYear = scan.nextInt();
		         
		         String birthYearPatternString = "\\d{4}";
		         Pattern birthYearPattern = Pattern.compile(birthYearPatternString);
		         Matcher birthYearMatcher = birthYearPattern.matcher(Integer.toString(birthYear));
		         
		         if(birthYearMatcher.find() && birthYear > 1900 && birthYear < 2018) {
		            break;
		         }
		         else {
		            System.out.print("1900-2018년 사이의 4자리 값을 입력하세요.\n");   
		         }
		      }
		      
		      //이메일 중복 검사(지우기 or 다시)

		      
		      
		      //입력받은 데이터로 고객 객체를 생성
		      Customer cust = new Customer(name, gender, email, birthYear);

		      //고객 객체를 ArrayList에 저장
		      custList.add(cust);
		   }
	   public static void fileSaveCustomerData() {
		      try {
		         BufferedWriter fw = new BufferedWriter(new FileWriter("C:\\Customer_list.txt"));
		         
		         for(int i = 0; i < count; i++) {
		            Customer cust = custList.get(i);
		            
		            fw.write("==========CUSTOMER INFO================");
		            fw.newLine();
		            fw.write("이름 : " + cust.getName());
		            fw.newLine();
		            fw.write("성별 : " + cust.getGender());
		            fw.newLine();
		            fw.write("이메일 : " + cust.getEmail());
		            fw.newLine();
		            fw.write("출생년도 : " + cust.getBirthYear());
		            fw.newLine();
		            fw.write("=======================================");
		            fw.newLine();
		         }
		         
		         fw.close();         
		      }catch(Exception e){
		         e.printStackTrace();
		      }
		   }
	//고객데이터 출력
	public static void printCustomerData(int index) {
		Customer cust = custList.get(index);
		System.out.println("==========CUSTOMER INFO================");
		System.out.println("이름 : " + cust.getName());
		System.out.println("성별 : " + cust.getGender());
		System.out.println("이메일 : " + cust.getEmail());
		System.out.println("출생년도 : " + cust.getBirthYear());
		System.out.println("=======================================");
	}

	//index 위치의 고객정보를 삭제합니다.
	public static void deleteCustomerData(int index) {
		custList.remove(index);
	}

	//index 위치의 고객 정보를 수정합니다.
	public static void updateCustomerData(int index) {
		Customer cust = custList.get(index);
		System.out.println("---------UPDATE CUSTOMER INFO----------");
		System.out.print("이름(" + cust.getName() + ") :");
		cust.setName(scan.next());

		System.out.print("성별(" + cust.getGender() + ") :");
		cust.setGender(scan.next().charAt(0));

		System.out.print("이메일(" + cust.getEmail() + ") :");
		cust.setEmail(scan.next());

		System.out.print("출생년도(" + cust.getBirthYear() + ") :");
		cust.setBirthYear(scan.nextInt());		
	}

}//end class