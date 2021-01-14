package com.cos.testWeb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.testWeb.service.UserService;
import com.cos.testWeb.user.User;
import com.cos.testWeb.user.dto.JoinReqDto;
import com.cos.testWeb.user.dto.LoginReqDto;
import com.cos.testWeb.util.Script;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		UserService userService = new UserService();

		if (cmd.equals("loginForm")) {
			RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
			dis.forward(request, response);
		}else if (cmd.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			User userEntity = userService.로그인(dto);
			if (userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("principal", userEntity);
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response,"로그인 실패!");
			}
		}else if (cmd.equals("joinForm")) {
			RequestDispatcher dis = request.getRequestDispatcher("user/joinForm.jsp");
			dis.forward(request, response);
		}else if (cmd.equals("join")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String userRole = request.getParameter("userRole");
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			dto.setUserRole(userRole);
			System.out.println("회원가입 : " + dto);
			int result = userService.회원가입(dto);
			if (result == 1) {
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response, "회원가입 실패!");
			}
		}else if (cmd.equals("usernameCheck")) {
			BufferedReader br = request.getReader();
			String username = br.readLine();
			System.out.println(username);
			int result = userService.아이디중복체크(username);
			PrintWriter out = response.getWriter();
			if (result == 1) {
				out.print("ok");
			}else {
				out.print("fail");
			}
			out.flush();
		}else if (cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		}else if (cmd.equals("userList")) {
			List<User> users = userService.회원목록보기();
			request.setAttribute("users", users);
			RequestDispatcher dis = request.getRequestDispatcher("user/userList.jsp");
			dis.forward(request, response);
		}else if (cmd.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = userService.회원삭제(id);
			HttpSession session = request.getSession();
			if (result == 1) {
				session.invalidate();
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response, "삭제를 실패했습니다.");
			}
		}else if (cmd.equals("deleteAdmin")) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = userService.회원삭제(id);
			if (result == 1) {
				System.out.println("삭제 완료");
			}else {
				Script.back(response, "삭제를 실패했습니다.");
			}
		}
	}
}