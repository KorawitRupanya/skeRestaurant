package code;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User interface for a menu and ordering system, using console interface. 
 * 
 * @author Korawit Rupanya
 */

public class SkeRestaurant {

	static String[] menuItems = RestaurantManager.getMenuItems();
	static double[] prices = RestaurantManager.getPrices();
	static ArrayList<Object> allOrder = new ArrayList<Object>();

	static Scanner my = new Scanner(System.in);
	static int quantity = 0;
	static double[] unitPrice;
	static String day;
	static int orderNumber;
	static private String[] menu;
	static private double[] price;
	static int[] quantities;
	static double[] result;
	static double total;
	static double subtotal = 0.00;
	static String memberRecord;
	static String promotion;
	static String dT;
	static protected DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
	static protected LocalDateTime now = LocalDateTime.now();

	/** Print menu.
	 */
	public static void intro() {
		menuItems = RestaurantManager.getMenuItems();
		unitPrice = RestaurantManager.getPrices();
		System.out.println("--------- Welcome to SKE Restaurant ---------");
		for (int i = 0; i <= menuItems.length - 1; i++) {
			System.out.printf("\t%2d. ", i + 1);
			for (int j = i; j <= i; j++) {
				System.out.printf("%-27s %6.2f Baht.\n", menuItems[j], unitPrice[j]);
			}
		}
	}

	public static void printOrder() {
		menu = RestaurantManager.getMenuItems();

		System.out.println("\n+--------------- Menu ---------------+-- Qty --+---- Price ----+");
		for (int k = 1; k <= result.length; k++) {
			if (quantities[k - 1] != 0 && quantities[k - 1] > 0) {
				System.out.printf("| %-35s|%7d  |%13.2f  |\n", menu[k - 1], quantities[k - 1], result[k - 1]);
			}
		}
		System.out.println("+------------------------------------+---------+---------------+");
	}

	public static void printMenuList() {
		menu = RestaurantManager.getMenuItems();
		price = RestaurantManager.getPrices();

		System.out.println("\n>> Menu Items List <<");
		for (int n = 0; n <= menu.length - 1; n++) {
			System.out.printf("\t%2d. ", n + 1);
			for (int k = n; k <= n; k++) {
				System.out.printf("%-27s %6.2f Baht.\n", menu[k], price[k]);
			}
		}
	}
	
	public static void printCommands() {
		System.out.println("\nPlease press command button to continue!");
		System.out.printf("\nPress :\t[o]:To order");
		System.out.println("\t(put ' - 'for reduce quantity)");
		System.out.printf("\t[v]:To veiw order");
		System.out.printf("\n\t[e]:To get receipt and Exit");
		System.out.printf("\n\t[m]:To show menu");
		System.out.printf("\n\t[p]:The SKE promotion (Try this after you have already done your order.)");
		System.out.printf("\n\t[?]:To show this commands\n");
	}

	/** This is for a special SKE promotion.
	 * If the user can answer the question right,he will be taken to random phase.
	 * If he is can answer the question correctly and lucky enough he might get a free meal.
	 */
	public static void SKEpromotion(int num1) {
		double vat;
		Random rand = new Random();
		int ran1 = rand.nextInt(3) + 1;
		if (num1 == ran1) {
			promotion = "Promotion(win)";
			System.out.println("WOW!!! You are real SKE and and a real gambler too 555!!!");
			System.out.println("+---------------------Here is you receipt----------------------+");
			now = LocalDateTime.now();
			dT = dtf.format(now);
			System.out.println("\nDate & Time : " + dT);
			printOrder();

			subtotal = total;
			vat = vat(subtotal);
			total = subtotal + vat;

			System.out.printf("|%-46s|%13.2f  |\n", " Subtotal :", subtotal);
			System.out.printf("|%-46s|%13.2f  |\n", " VAT 7% :", vat);
			System.out.printf("|%-46s|%13.2f  |\n", " Total :", total);
			System.out.printf("|%-46s|%13.2f  |\n", " Discount from the promotion :", -total);
			total = 0;
			System.out.printf("|%-46s|%13.2f  |\n", " Total :", total);
			System.out.println("+----------------------------------------------+---------------+");
		} else {
			System.out.println("Sorry...You miss it!");
			printReceipt();
		}
	}

	public static double vat(double subtotal) {
		double vat = (subtotal * 7) / 100;
		return vat;
	}

	public static double memberdiscount(double total) {
		double discount = (total * 10) / 100;
		return discount;
	}
	
	public static double[] getUnitTotal() {
		for(int x = 0;x < menuItems.length;x++) {
			result[x] = quantities[x]*prices[x];
		}
		return result;
	}
	
	public static double getTotal() {
		total = 0.0;
		for(int x = 0;x < menuItems.length;x++) {
			total += quantities[x]*prices[x];
		}
		return total;
	}

	public static void printReceipt() {
		double vat;

		System.out.println("+---------------------Here is you receipt----------------------+");
		now = LocalDateTime.now();
		dT = dtf.format(now);
		System.out.println("\nDate & Time : " + dT);
		printOrder();
		
		subtotal = total;
		vat = vat(subtotal);
		total = subtotal + vat;

		System.out.printf("|%-46s|%13.2f  |\n", " Subtotal :", subtotal);
		System.out.printf("|%-46s|%13.2f  |\n", " VAT 7% :", vat);
		System.out.printf("|%-46s|%13.2f  |\n", " Total :", total);
		System.out.println("+----------------------------------------------+---------------+");
	}

	public static void printReceiptwithMember() {
		double vat, discount;

		System.out.println("+---------------------Here is you receipt----------------------+");
		now = LocalDateTime.now();
		dT = dtf.format(now);
		System.out.println("\nDate & Time : " + dT);
		printOrder();

		subtotal = total;
		vat = vat(subtotal);
		discount = memberdiscount(subtotal);
		total = (subtotal + vat) - discount;

		System.out.printf("|%-46s|%13.2f  |\n", " Subtotal :", subtotal);
		System.out.printf("|%-46s|%13.2f  |\n", " VAT 7% :", vat);
		System.out.printf("|%-46s|%13.2f  |\n", " member discount 10% :", discount);
		System.out.printf("|%-46s|%13.2f  |\n", " Total :", total);
		System.out.println("+----------------------------------------------+---------------+");
	}

	/** Contains logic about how user choose choices.
	 */
	public static void choices() {
		while (true) {
			System.out.print("Enter your command: ");
			String choice = my.next();
			if (choice.equals("?")) {
				printCommands();
			}
			if (choice.equals("o")) {
				while (true) {
					System.out.print("\nEnter menu item / Command: ");
					if (my.hasNextInt()) {
						orderNumber = my.nextInt();
						if (orderNumber >= 1 && orderNumber <= prices.length) {
							System.out.print("Enter Quantity: ");
							quantity = my.nextInt();
							quantities[orderNumber - 1] +=quantity;
							if (quantities[orderNumber - 1] < 0) {
								quantities[orderNumber - 1] = 0;
							}
							result = getUnitTotal();
							total = getTotal();
						}
					} 
				else {
						choice = my.next();
						if (choice.equals("v")) {
							printOrder();
							break;
						} else break;
					}
				}
			}
			if (choice.equals("m")) {
				printMenuList();
			}
			if (choice.equals("e")) {
				System.out.print("Do you have member card Y/N?");
				String member = my.next();
				if (member.equals("Y")) {
					memberRecord = "Member";
					printReceiptwithMember();
				} else if (member.equals("N")) {
					printReceipt();
				}
				break;
			}
			if (choice.equals("p")) {
				promotion = "Promotion(lose)";
				System.out.println(
						"If you can answer this question right,you will be taken to random phase.\nIf you are real SKE and lucky enough you might get a free meal!!!\nWhat is code name of java version J2SE 5.0 that was released on September 30, 2004.?");
				System.out.println("Choice 1.CAT 2.TIGER 3.LION 4.CHEETAH 5.LEOPARD");
				System.out.print("Enter your answer:");
				int answer = my.nextInt();
				if (answer == 2) {
					System.out.print("CONGRATULATION!!! Please enter number 1,2 or 3 : ");
					int num1 = my.nextInt();
					SKEpromotion(num1);
					break;
				} else {
					System.out.println("Sorry you are not real SKE?");
					System.out.println("+------------Here is you receipt---------+");
					printReceipt();
					break;
				}
			}
		}
	}
	
	/** Get data for writing receipt.
	 * 
	 */
	public static String getData() {
		String allOrder = "";
		for (int k = 1; k <= result.length; k++) {
			if (quantities[k - 1] != 0 && quantities[k - 1] > 0) {
				String Orders = menu[k - 1] + ", " + quantities[k - 1] + " @ " + result[k - 1] + "\n";
				allOrder = allOrder.concat(Orders);
			}
		}
		String finalTotal = "Total : " + total + "\n\n";
		allOrder = allOrder.concat(finalTotal);
		return allOrder;
	}

	public static void recordReceipt() {
		String receipt = getData();
		OrderRecord orderRecord = new OrderRecord(dT, receipt, memberRecord, promotion);
		allOrder.add(orderRecord);
	}

	public static void main(String[] args) throws IOException {
		String cha = "";
		RestaurantManager.loadMenu();
		do {
			prices = RestaurantManager.getPrices();
			quantities = new int[prices.length];
			result = new double[prices.length];
			dT = "";
			memberRecord = "Non-member";
			promotion = "Non-promotion";
			subtotal = 0.0;
			total = 0.0;
			intro();
			printCommands();
			choices();
			getData();
			recordReceipt();
			System.out.print("\n[M]ake new order or [Q]uit : ");
			cha = my.next();
			if(cha.equals("Q")) break;
		}while(cha.equals("M"));
		RestaurantManager.writeReceipt(allOrder);
	}
}