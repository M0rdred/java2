package hu.mik.java2.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SzorzoTablaTag extends SimpleTagSupport {

	private int rowNum;
	private int colNum;

	@Override
	public void doTag() throws JspException, IOException {
		//JspFragment jspBody = getJspBody();
		StringWriter sw = new StringWriter();
		//jspBody.invoke(sw);
		JspWriter out = getJspContext().getOut();
		out.print("<table>");

		for (int i = 0; i < rowNum; i++) {
			out.print("<tr>");
			for (int j = 0; j < colNum; j++) {
				out.print("<td>");
				out.print(i + " x " + j + " = " + (i * j));
				out.print("</td>");
			}
			out.print("</tr>");
		}
		out.print("</table>");
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
}
