package send;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import command.Command;

public class Email implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name =request.getParameter("name");
		String email = request.getParameter("email");
		String message = request.getParameter("message");
		String myemail = "tmddhks0127@gmail.com";
				
		JSONObject obj = new JSONObject();
			
			// 네이버일 경우 smtp.naver.com 을 입력합니다. 
			// Google일 경우 smtp.gmail.com 을 입력합니다. 
			String host = "smtp.gmail.com"; 
			
			final String username = "donghyeon0127"; //네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요. 
			final String password = "hbee0595!@12"; //네이버 이메일 비밀번호를 입력해주세요. 
			int port=465; //포트번호 // 메일 내용 
            
            String recipient = myemail; //받는 사람의 메일주소를 입력해주세요. 
			String subject = "Kang's portfolio 에서의 메세지"; //메일 제목 입력해주세요. 
			String body = "연락을 보내 주신 분은 "+name+" 입니다\n연락을 보내 주신 분의 이메일은 : "+email+" 입니다\n내용은 : "+message; //메일 내용 입력해주세요. 
			
			Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성 // SMTP 서버 정보 설정 
			
			props.put("mail.smtp.host", host); 
			props.put("mail.smtp.port", port); 
			props.put("mail.smtp.auth", "true"); 
			props.put("mail.smtp.ssl.enable", "true"); 
			props.put("mail.smtp.ssl.trust", host); 
			
			//Session 생성
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				String un=username; 
				String pw=password; 
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() { 
					return new javax.mail.PasswordAuthentication(un, pw); 
					} 
			}); 
			session.setDebug(true); //for debug 
			
			Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
			mimeMessage.setFrom(new InternetAddress("tmddhks0127@gmail.com")); //발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요. 
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음 
			mimeMessage.setSubject(subject); //제목셋팅
			mimeMessage.setText(body); //내용셋팅
			Transport.send(mimeMessage); //javax.mail.Transport.send() 이용 
			obj.put("msg", "successfully sent an e-mail");
			obj.put("ok","ok");
			HttpSession sessions = request.getSession();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-json,Charset=utf-8");
			response.getWriter().print(obj);
			
		}
		
	}

