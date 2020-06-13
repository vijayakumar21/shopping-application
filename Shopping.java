
import java.sql.*;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;  
import java.util.*;

class product // a product 
{
	String model;
	String comp;
	String price;
	String specs;
	int count;
	String section;
	product(String m1,String comp1,String pri1,String sp1,String sec)
	{
		model=m1;
		comp=comp1;
		price=pri1;
		specs=sp1;
		count=0;
		section=sec;
	}
}





public class Shopping {
static JFrame f;
static JPanel one;
static CardLayout cl;
static Statement smt;
static ArrayList <product> mycart;// cart collections
static JPanel mobpanel,wmpanel,tvpanel,acpanel,cartpanel,checkoutpanel,historypanel;
static int total;
static product mprod[];

private static final String url = "jdbc:mysql://localhost:3306/Shopping";
private static final String user = "root";
private static final String password = "bhushanabc";
private static String loginUser, finaladd ,cdno,modepay;
private static String loginAdd;
private static String loginPh,un;
private static String loginEid;
static Connection con;

static void initializeProductsArray(String section)// initializing array of products according to the section
{
	try{
		smt=con.createStatement();
		ResultSet rs=smt.executeQuery("SELECT count(*) AS county FROM "+section);
 		int c=0;
 		while(rs.next())
 			c=rs.getInt("county");
 		mprod=new product[c];
 		int i=0;
 		rs=smt.executeQuery("Select * from "+section);
 		while(rs.next())
 		{
 			String m=rs.getString("model");
 			String co=rs.getString("comp");
 			String s="",p;
 			p=rs.getString("price");
 			if(section.equalsIgnoreCase("Mobile") ||section.equalsIgnoreCase("TV"))
 				s=rs.getString("specs");
 			 
 			if(section.equalsIgnoreCase("Mobile") ||section.equalsIgnoreCase("TV"))
 				mprod[i]=new product(m,co,p,s,section);
 			else
 				mprod[i]=new product(m,co,p,null,section);
 			i+=1;
 		}}
 		catch(Exception ex)
 		{
 			ex.printStackTrace();
 		}

	
}
static void historyFind(JPanel h)
{

	h.removeAll();
	int start=45;
	JLabel lb = new JLabel(" ORDER HISTORY ",SwingConstants.CENTER);
	lb.setBounds(540,0,120,30);
	lb.setForeground(Color.WHITE);

	JPanel two=new JPanel(null);
	two.setBackground(Color.GRAY);
	two.setBounds(0,0,1150,30);

	JButton back=new JButton("<--");
	back.setBounds(0,0,70,30);
	two.add(lb);two.add(back);
	h.add(two);

	back.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		cl.show(one,"home");


	}});


	
	try
	{
		smt=con.createStatement();
	ResultSet rs1=smt.executeQuery("SELECT * from sold where username ="+"\""+un+"\"");
	while(rs1.next())
	{
		String ad=rs1.getString("address");
		String mod=rs1.getString("model");
		String compa=rs1.getString("company");
		String pri =rs1.getString("price");
		String qt=rs1.getString("quantity");
		String mod2=rs1.getString("mode");
		String un1=rs1.getString("username");
		String d=rs1.getString("date");

		JLabel adl=new JLabel(ad);
		JLabel modl=new JLabel(mod);
		JLabel compal=new JLabel(compa);
		JLabel pril=new JLabel(pri+"x"+qt);
		JLabel modl2=new JLabel(mod2);
		JLabel unl=new JLabel(un1);
		JLabel dl=new JLabel(d);

		adl.setBounds(30,start,210,30);
		modl.setBounds(255,start,60,30);
		compal.setBounds(325,start,120,30);
		pril.setBounds(460,start,60,30);
		modl2.setBounds(545,start,150,30);
		unl.setBounds(710,start,60,30);
		dl.setBounds(805,start,100,30);

		h.add(adl);h.add(modl);
		h.add(compal);h.add(pril);
		h.add(modl2);h.add(unl);
		h.add(dl);

		start+=45;

	}
}

catch(Exception e)
{
	e.printStackTrace();
}
}




static void initfinaladd(String s)
{
	finaladd=s;
}

static void initcard(String s)
{
	cdno=s;
}
static void initmodepay(String s)
{
	modepay=s;
}
static void init()
{
	cdno=""; modepay=""; finaladd="";
}
static void initcart()
{
	mycart=new ArrayList<product>();
}

static void sold() // add history of buying 
{
	try{
	for (product i:mycart)
	{
		smt=con.createStatement();
		smt.executeUpdate("INSERT INTO sold VALUES (\""+finaladd+"\",\""+i.model+"\",\""+i.comp+"\",\""+i.count+"\",\""+i.price+"\",\""+i.section+"\",\""+modepay+"\",\""+un+"\",\""+"03/10/2019"+"\")");
	}
}
	catch(Exception sq)
	{
		sq.printStackTrace();
	}
}
public static void DBcon()
{   cdno=""; modepay=""; finaladd="";
	try {
            con = DriverManager.getConnection(url, user, password);
            
 
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    mycart=new ArrayList<product>();
}
public static void calcTotal()
{   total=0;
	for(product i:mycart)
		total+=(Integer.parseInt(i.price)*i.count);
}
public static void addprod(product x)//adding product to cart collections

{
	if(mycart.contains(x))
	{
		int pos=mycart.indexOf(x);
		mycart.get(pos).count+=1;
	}
	else{
		x.count=1;
		mycart.add(x);
	}
	
}
public static void prepareWindow()
{
	try{
	smt=con.createStatement();
}
catch(Exception e2)
{
	e2.printStackTrace();
}
/*                                          one common frame                          */


f = new JFrame("ShopOnBoard.com"); // a single frame 
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.setSize(1150,640);
//f.setResizable(false);

cl=new CardLayout();
one=new JPanel(cl);


/*                                signup                           */
JPanel signup = new JPanel(new GridLayout(1, 2)); 
JLabel label = new JLabel();
label.setIcon(new ImageIcon("13.jpg"));// your image here

JPanel inner = new JPanel(new GridLayout(13, 1)); 

JTextField tf=new JTextField();

inner.add(new Label("Name :"));
inner.add(tf);
inner.add(new Label("Email ID :"));
JTextField eid=new JTextField();
inner.add(eid);
inner.add(new Label("Phone Number :"));
JTextField ph=new JTextField();
inner.add(ph);
inner.add(new Label("Address :"));
JTextField addr=new JTextField();
inner.add(addr);
JLabel errorL = new JLabel(" ",SwingConstants.CENTER);
inner.add(errorL);
JButton signupbtn=new JButton("Sign up");
signupbtn.setRolloverEnabled(true);

signupbtn.addActionListener(new ActionListener() { 
 public void actionPerformed(ActionEvent e) { 
 	String namev=tf.getText().trim();
 	String eidv=eid.getText().trim();
 	String addv=addr.getText().trim();
 	String phs=ph.getText().trim();

 	if(namev.isBlank()|| eidv.isBlank() ||addv.isBlank() ||phs.isBlank())
 		{
 			errorL.setText("Fill all fields properly");
 		    errorL.setForeground(Color.RED);
 		}
 	else
 		{
 			try
 			{
 				if(phs.length()!=10)
 					throw new StringIndexOutOfBoundsException("only 10 digit");
 				long phv=Long.parseLong(phs);
 				int c=1;
 				
 				ResultSet rs=smt.executeQuery("SELECT count(*) AS county FROM LoginDetails WHERE email=\""+eidv+"\"");
 				while(rs.next())
 					c=rs.getInt("county");
 			
 				

 				if(c==0){
 				tf.setText("");eid.setText("");ph.setText("");addr.setText("");
 				errorL.setText("Check for login details in your Email");
 				errorL.setForeground(Color.GREEN.darker().darker());
 				StringTokenizer ob=new StringTokenizer(eidv,"@");
 				String temp=new String(ob.nextToken());
 				Random r =new Random();
 				int nu =r.nextInt(1200);
 				String usname=namev.charAt(0)+(temp.substring(temp.length()-3))+namev.charAt(namev.length()-1)+Integer.toString(nu);
 				Random rand = new Random();
 				int num = rand.nextInt(1000);
 				String passt=temp.substring(temp.length()-4).toUpperCase()+phs.substring(6)+Integer.toString(num);
 				String st = "Username : "+usname+"\n"+"Password : "+passt;
 				smt.executeUpdate("INSERT INTO LoginDetails VALUES (\""+namev+"\",\""+eidv+"\",\""+addv+"\","+phs+",\""+usname+"\",\""+passt+"\")");
 				JOptionPane.showMessageDialog(null,st);
 			}
 			else {
 				errorL.setText("User already exists");
 				errorL.setForeground(Color.RED);
 			}
 				
 			}
 			
 			 catch(Exception e1)
 			{
 				errorL.setText("Enter valid phone number");
 				errorL.setForeground(Color.RED);
 			}

 		}
 	} 
 });

inner.add(signupbtn);
inner.add(new Label(" "));
JLabel ahacc=new JLabel("Already have an account ?",SwingConstants.CENTER);
ahacc.setForeground(Color.RED.darker().darker());
inner.add(ahacc);
JButton signinbtn1=new JButton("Sign in");
inner.add(signinbtn1);
signinbtn1.addActionListener(new ActionListener() { 
 public void actionPerformed(ActionEvent e) { 
 	cl.show(one,"signin");
   
 } 
} );


signup.add(label); //image
signup.add(inner); //signup page	


/*                                signin                         */

JPanel signin = new JPanel(new GridLayout(1,2));
JLabel label1 = new JLabel();
label1.setIcon(new ImageIcon("12.png"));
signin.add(label1);
JPanel inner1 = new JPanel(new GridLayout(7, 1)); 
JTextField signinuser=new JTextField();

JPasswordField signinpw=new JPasswordField();
signinpw.setEchoChar('*');//jPasswordField1.setEchoChar((char)0)
JButton signinbtn=new JButton("Sign in");
inner1.add(new JLabel("LOGIN",SwingConstants.CENTER));
JLabel ins=new JLabel(" ",SwingConstants.CENTER);

inner1.add(new JLabel("Username or Email ID"));
inner1.add(signinuser);
inner1.add(new JLabel("Password"));
inner1.add(signinpw);
inner1.add(ins);
inner1.add(signinbtn);
signin.add(inner1);
signinbtn.addActionListener(new ActionListener() { 
 public void actionPerformed(ActionEvent e) { 
 	String uname=signinuser.getText().trim();
 	un=uname;
 	String pass=(new String(signinpw.getPassword())).trim();
 	if(uname.isBlank() || pass.isBlank())
 	{
 		ins.setText("Fill both credentials");
 		ins.setForeground(Color.RED);

 	}
 	else
 	{try{
 		ResultSet rs1=smt.executeQuery("SELECT count(*) AS county FROM LoginDetails WHERE uname=\""+uname+"\""+" OR email =\""+uname+"\"");
 		int c1=1;
 		while(rs1.next())
 			c1=rs1.getInt("county");
 		if(c1==0)
 		{
 			ins.setText("Username does not exists");
 			ins.setForeground(Color.RED);
 		}
 		else
 		{   
 			ResultSet rs2=smt.executeQuery("SELECT password FROM LoginDetails WHERE uname=\""+uname+"\""+" OR email =\""+uname+"\"");
 			String tempas="";
 			while(rs2.next())
 				tempas=rs2.getString("password");
 			if(tempas.equals(pass))
 				{   
 					rs2=smt.executeQuery("SELECT name,email,address,phone FROM LoginDetails WHERE uname=\""+uname+"\""+" OR email =\""+uname+"\"");
 					while(rs2.next())
 					{
 						loginUser=rs2.getString("name");
 						loginAdd=rs2.getString("address");
 						loginEid=rs2.getString("email");
 						loginPh=rs2.getString("phone");
 					}
 					ins.setText("");
 					cl.show(one,"home");
 			}
 			else{
 				ins.setText("Incorrect password, try again");
 			    ins.setForeground(Color.RED);
 			}
 		}}
 		catch(Exception e3)
 		{
 			e3.printStackTrace();
 		}

 	}
 	
   }
});


/*                                home                          */

JPanel home=new JPanel(new GridLayout(2,1));
JButton mbtn=new JButton("MOBILES");
mbtn.setRolloverEnabled(true);
mbtn.setIcon(new ImageIcon("mof1.jpg"));
JButton wbtn=new JButton("WASHING MACHINE");
wbtn.setIcon(new ImageIcon("wash.jpg"));
JButton abtn=new JButton("AIR CONDITIONERS");
JButton tbtn=new JButton("TELEVISION");
tbtn.setIcon(new ImageIcon("r1.jpg"));
abtn.setIcon(new ImageIcon("mob112.jpg"));

JPanel p1=new JPanel(new GridLayout(2,1));
JPanel p2=new JPanel(new GridLayout(2,1));
JPanel home0=new JPanel(null);
home0.setSize(1150,320);

JPanel two=new JPanel(null);
two.setBounds(0,0,1150,30);
two.setBackground(Color.GRAY);

JButton cart=new JButton("Cart");
cart.setBounds(1076,0,70,30);
two.add(cart);
cart.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{
		refreshCart("home");
		cl.show(one,"cart");
	}
});

JButton log=new JButton("Logout");
log.setBounds(4,0,80,30);
two.add(log);
log.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{
		mycart=new ArrayList<product>();
        signinuser.setText("");
        signinpw.setText("");
        errorL.setText("");
		cl.show(one,"signup");
	}
});

JButton profile=new JButton("");
profile.setBounds(1015,0,60,30);
profile.setIcon(new ImageIcon("prof1.png"));
two.add(profile);
JLabel tit=new JLabel("HOME");
tit.setForeground(Color.WHITE);
tit.setBounds(540,0,70,30);
two.add(tit);
JLabel iphone=new JLabel();
iphone.setIcon(new ImageIcon("iphone1.jpg"));
iphone.setBounds(0,30,1150,290);
home0.add(iphone);




home0.add(two);

JPanel home1=new JPanel(new GridLayout(1,2));

profile.addActionListener(new ActionListener() { 
	 public void actionPerformed(ActionEvent e) {
		 historyFind(historypanel);
	 	cl.show(one,"history");
	   
	 }
	});

mbtn.addActionListener(new ActionListener() { 
	 public void actionPerformed(ActionEvent e) {
	 	initializeProductsArray("mobile");
		 refreshProducts(mobpanel,mprod,"Mobiles");
	 	cl.show(one,"Mobiles");
	   
	 }
	});
wbtn.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		initializeProductsArray("wm");
		refreshProducts(wmpanel,mprod,"Washing Machines");
		cl.show(one,"Washing Machines");

	}

});

tbtn.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		initializeProductsArray("tv");
		refreshProducts(tvpanel,mprod,"Television");
		cl.show(one,"Television");

	}

});

abtn.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		initializeProductsArray("ac");
		refreshProducts(acpanel,mprod,"Air Conditioners");
		cl.show(one,"Air Conditioners");

	}

});

p1.add(mbtn);
p1.add(wbtn);
p2.add(tbtn);
p2.add(abtn);

home.add(home0);
home1.add(p1);
home1.add(p2);
home.add(home1);
cartpanel=new JPanel(null);
mobpanel=new JPanel(null);
wmpanel=new JPanel(null);
tvpanel=new JPanel(null);
acpanel=new JPanel(null);
historypanel=new JPanel(null);
checkoutpanel=new JPanel(null);
mobpanel.setPreferredSize(new Dimension(500, 8000));
mobpanel.setBackground(Color.WHITE);
wmpanel.setBackground(Color.WHITE);
tvpanel.setBackground(Color.WHITE);
acpanel.setBackground(Color.WHITE);
wmpanel.setPreferredSize(new Dimension(500, 3750));
tvpanel.setPreferredSize(new Dimension(500, 3990));
acpanel.setPreferredSize(new Dimension(500, 3750));
cartpanel.setPreferredSize(new Dimension(500, 1300));
checkoutpanel.setPreferredSize(new Dimension(500,1300));
historypanel.setPreferredSize(new Dimension(500,1300));
JScrollPane pane = new JScrollPane(mobpanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
JScrollPane pane1 = new JScrollPane(wmpanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
JScrollPane pane2 = new JScrollPane(tvpanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
JScrollPane pane3 = new JScrollPane(acpanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
JScrollPane pane4=new JScrollPane(cartpanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
JScrollPane pane5=new JScrollPane(checkoutpanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
JScrollPane pane6=new JScrollPane(historypanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


one.add("signup",signup);
one.add("signin",signin); //adding everything to card 
one.add("home",home);
one.add("Mobiles",pane);
one.add("Washing Machines",pane1);
one.add("Television",pane2);
one.add("Air Conditioners",pane3);
one.add("cart",pane4);
one.add("checkout",pane5);
one.add("history",pane6);

f.add(one); //frame contains main(one) card

f.setVisible(true);
}




static void refreshCart(String bac)/*                                cart                          */
{
	cartpanel.removeAll(); //remove all items
	JLabel lb = new JLabel(" CART ");
	lb.setBounds(540,0,120,30);
	lb.setForeground(Color.WHITE);
	JButton back=new JButton("<--");
	back.setBounds(0,0,70,30);
	back.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		cl.show(one,bac);

	}});
	
	JPanel two=new JPanel(null);
	two.setBackground(Color.GRAY);
	two.setBounds(0,0,1150,30);
	
	JButton check=new JButton("Check out");
	check.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		checkout(bac);
		cl.show(one,"checkout");

	}

});
	check.setBounds(1000,0,130,30);
	two.add(check);
	two.add(back);
	two.add(lb);
	cartpanel.add(two);

	if(mycart.size()==0)
	{
		check.setVisible(false); 
		JLabel empty=new JLabel("Your Cart is Empty , Go back and shop ",SwingConstants.CENTER);
		empty.setBounds(83,108,1000,400);
		empty.setFont(new Font("Serif", Font.PLAIN, 50));
		cartpanel.add(empty);
		return;
	}
	int i;
	
	for(i=0;i<mycart.size();i++)
	{
			JPanel item=new JPanel(null);
			item.setBounds(0,50+50*i,1150,40);
			int c=mycart.get(i).count;
			int ref=c;
			String m=mycart.get(i).model;
			String com=mycart.get(i).comp;
			String p=mycart.get(i).price;
			String sec=mycart.get(i).section;

			JLabel prname=new JLabel(com+" "+m); 
			prname.setBounds(10,5,400,30);

			JLabel price=new JLabel("Rs."+p); 
			price.setBounds(415,5,200,30);
			final int ji=i;
			total+=c*Integer.parseInt(p);

			JLabel qty=new JLabel(""+c+"X"+p+"="+(c*Integer.parseInt(p))); 
			qty.setBounds(620,5,300,30);

			JButton remove=new JButton("Remove Item");
			remove.setBounds(950,5,150,30);
			remove.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					
				 
				 if(mycart.get(ji).count==1)
				 	mycart.remove(ji);
				 else
				 	mycart.get(ji).count-=1;
				 calcTotal();
				 refreshCart(bac);
				 cl.show(one,"home");
				 cl.show(one,"cart");
				}
			});
			
			item.add(prname);
			item.add(price);
			item.add(qty);
			item.add(remove);

			cartpanel.add(item);
	}
	calcTotal();

	JLabel totlabel=new JLabel("Total :"+total);
	totlabel.setBounds(610,50+50*i+40,200,30);
	cartpanel.add(totlabel);
	


}
static void checkout(String bac)/*       checkout          */
{

	checkoutpanel.removeAll(); //remove all items
	finaladd=loginAdd; String cardno;
	JLabel lb = new JLabel(" CHECKOUT ");
	lb.setBounds(540,0,120,30);
	lb.setForeground(Color.WHITE);

	JButton back=new JButton("<--");
	back.setBounds(0,0,70,30);
	back.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		refreshCart(bac);
		cl.show(one,"cart");
		init();

	}});

	
	
	JPanel two=new JPanel(null);
	two.setBackground(Color.GRAY);
	two.setBounds(0,0,1150,30);
	JButton h=new JButton("Home");
	h.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		cl.show(one,"home");
		init();
	}});
	h.setBounds(1000,0,130,30);
	two.add(h);
	two.add(back);
	two.add(lb);
	checkoutpanel.add(two);

	int i;
	
	for(i=0;i<mycart.size();i++)
	{
			JPanel item=new JPanel(null);
			item.setBounds(0,50+50*i,1150,40);
			int c=mycart.get(i).count;
			int ref=c;
			String m=mycart.get(i).model;
			String com=mycart.get(i).comp;
			String p=mycart.get(i).price;

			JLabel prname=new JLabel(com+" "+m); //increase font later
			prname.setBounds(10,5,400,30);

			JLabel price=new JLabel("Rs."+p); //increase font later
			price.setBounds(415,5,200,30);
			final int ji=i;
			total+=c*Integer.parseInt(p);

			JLabel qty=new JLabel(""+c+"X"+p+"="+(c*Integer.parseInt(p))); //increase font later
			qty.setBounds(620,5,300,30);

			
			
			item.add(prname);
			item.add(price);
			item.add(qty);
			

			checkoutpanel.add(item);
	}
	calcTotal();
	int flag=0;
	JLabel totlabel=new JLabel("Total :"+total);
	totlabel.setBounds(610,50+50*i+40,200,30);
	checkoutpanel.add(totlabel);
	JButton change=new JButton("Do you want to change the shipping address?");
	change.setBounds(444,50+50*i+40+60,325,40);
	JLabel present =new JLabel("Address :   "+loginUser+", "+loginAdd);
	present.setBounds(415-60,50+50*i+40+60+55,600,40);
	JLabel newadd=new JLabel("New Address :");
	newadd.setBounds(415-60,50+50*i+40+60+110,95,40);
	JTextField newa=new JTextField();
	newa.setBounds(520-60,50+50*i+40+60+110,300,40);
	JLabel err=new JLabel("");
	err.setVisible(false);
	JLabel df=new JLabel("0");
	
	err.setBounds(550,50+50*i+40+60+230-65+80+30,200,50);
	JButton okay=new JButton("Okay");
	okay.setBounds(830-65,50+50*i+40+60+110,70,40);
	newa.setVisible(false);newadd.setVisible(false);okay.setVisible(false);
	change.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			newa.setVisible(true);newadd.setVisible(true);okay.setVisible(true);
			
		}
	});
	okay.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(newa.getText().equals("")){
				err.setText("Enter a valid address");
				err.setForeground(Color.RED);
				err.setVisible(true);
			}
			else{
			present.setText("Address : "+newa.getText());
			newa.setVisible(false);newadd.setVisible(false);okay.setVisible(false);
			err.setVisible(false);
			initfinaladd(newa.getText());
	
		}
		}
	});
	
	

	JLabel pay=new JLabel("Payment Method : ");
	pay.setBounds(415-60,50+50*i+40+60+110+50,280,40);
	JButton cash=new JButton("Cash on delivery");
	cash.setBounds(520-60,50+50*i+40+60+110+50+45,165,30);
	JButton card=new JButton ("Credit / Debit card");
	card.setBounds(520-60+175,50+50*i+40+60+110+50+45,165,30);
	JTextField no= new JTextField("Card number");
	no.setBounds(520-60+75,50+50*i+40+60+110+50+45+30+10,180,30);
	no.setVisible(false);

	card.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{ 
			pay.setText("Payment Method :  "+card.getText());
			no.setVisible(true);
			initmodepay(card.getText());
			
		}
	});

	
	String str1;str1=cash.getText();
	cash.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{ 
			pay.setText("Payment Method :  "+cash.getText());
			initmodepay(cash.getText());
			
			
			no.setVisible(false);
			}
	});


	JButton placeorder=new JButton("Place Order");
	placeorder.setBounds(520,50+50*i+40+60+230+70+30,200,50);
	placeorder.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(modepay.equals("Credit / Debit card"))
			{
				if(no.getText().matches("[0-9]+") && no.getText().length()==16)
				{
					err.setText("      Order Placed");
					err.setForeground(Color.GREEN.darker().darker());
					err.setVisible(true);
					sold();
					init();
					initcart();
					placeorder.setVisible(false);
					card.setVisible(false);
					cash.setVisible(false);
					change.setVisible(false);
					no.setVisible(false);
				}
				else{
					err.setText("Enter a valid card number");
					err.setForeground(Color.RED);
					err.setVisible(true);

				}
			}
			else if(modepay.equals(""))
			{
				err.setText("Select a mode of payment");
					err.setForeground(Color.RED);
					err.setVisible(true);


			}
		
		else
		{
			err.setText("      Order Placed");
					err.setForeground(Color.GREEN.darker().darker());
					err.setVisible(true);
					sold();
					init();
					initcart();
					placeorder.setVisible(false);
					card.setVisible(false);
					cash.setVisible(false);
					change.setVisible(false);
					no.setVisible(false);

		}
			
		
		}
	});

	checkoutpanel.add(change);
	checkoutpanel.add(present);
	checkoutpanel.add(newadd);
	checkoutpanel.add(newa);
	checkoutpanel.add(okay);
	checkoutpanel.add(placeorder);
	checkoutpanel.add(err);
	checkoutpanel.add(pay);
	checkoutpanel.add(cash);
	checkoutpanel.add(card);
	checkoutpanel.add(no);


}
static void refreshProducts(JPanel pan, product prod[],String title) /*               single    function for all 4 sections          */
 {     
	pan.removeAll();
	JLabel moblabel = new JLabel("");
	moblabel.setText(title);
	moblabel.setBounds(540,0,120,30);
	moblabel.setForeground(Color.WHITE);
	JButton back=new JButton("<--");
	back.setBounds(0,0,70,30);
	back.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		cl.show(one,"home");

	}

});

	JButton cart=new JButton("Cart");
	cart.setBounds(1060,0,70,30);
	JPanel two=new JPanel(null);
	two.setBackground(Color.GRAY);
	two.setBounds(0,0,1150,30);
	two.add(back);
	two.add(moblabel);
	two.add(cart);
	pan.add(two);
	

	int i;
	int y=90-480;
	int x1=25;
	int x2=650; //half
	int c=1;
	for(i=0;i<prod.length;i++) {
		
		int x=(c%2==0)? x2:x1;
		
		if(c%2==0){	
			x=x2;
		}
		else {
			x=x1;y=y+550;	
		}
		
		JPanel product=new JPanel(null);
		product.setBounds(x,y,415,480);
		product.setBackground(Color.WHITE);
		final product prof=prod[i];
		JLabel img=new JLabel("IMG",SwingConstants.CENTER);

		img.setIcon(new ImageIcon(prod[i].comp+prod[i].model+".jpg"));
		img.setBounds(0,0,415,415);
		JLabel name=new JLabel(prod[i].comp+" "+prod[i].model,SwingConstants.CENTER);
		name.setBounds(0,416,415,20);
		JLabel price=new JLabel("Rs."+prod[i].price,SwingConstants.CENTER);
		price.setBounds(0,436,415,20);
		JButton addcart=new JButton("Buy / Add to Cart");
		addcart.setBounds(100,456,215,20);
		//addcart.setForeground(Color.GREEN.darker().darker().darker());
		//addcart.setIcon(new ImageIcon("cart.jpg"));

		addcart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
				{
					addprod(prof);//adding that product to cart
				}
			});
		cart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
				{
					
					refreshCart(title);
					cl.show(one,"cart");
				}
			});
		product.add(img);
		product.add(name);
		product.add(price);
		product.add(addcart);
		c++;
		pan.add(product);
		}
	}
public static void main(String[] args) {


	DBcon();// connect to Db
    prepareWindow(); // initializes signup, signin and home page 
    }
}
