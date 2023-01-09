package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import utils.DBUtil;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager  em = DBUtil.createEntityManager();//これからDBの操作をしますよ～っておまじない
        //Springが用意するトランザクションでは対応できない場合に、明示的トランザクションを使うgetTransaction
        //トランザクション処理⇒処理がどこかでうまくいかなかったら全体の処理がキャンセルになる
        em.getTransaction().begin();//ここから登録とかの処理を行いますよ～って感じの文

        // Messageのインスタンスを生成
        Message m = new Message();

        // mの各フィールドにデータを代入
        String title = "taro";
        m.setTitle(title);

        String content = "hello";//ここの値は入力できるように後でコード変更
        m.setContent(content);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        m.setCreated_at(currentTime);
        m.setUpdated_at(currentTime);

        //データベースに保存
        em.persist(m);//データベースに保存
        em.getTransaction().commit();//データの新規登録を確定(コミット）させる命令

        //自動採番されたIDの値を表示
        response.getWriter().append(Integer.valueOf(m.getId()).toString());

        em.close();
    }

}