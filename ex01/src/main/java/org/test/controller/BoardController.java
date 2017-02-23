package org.test.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.test.domain.BoardVO;
import org.test.domain.Criteria;
import org.test.domain.PageMaker;
import org.test.service.BoardService;

/****************************************
 * 
 * Controller�� ���������� ��ü���� �������� �����ϴ� ���̴�. �Ѱ��� ������ �������� �������� �� �� �ְ�, �ٸ� �������� �̵��ϴ�
 * ���� ���� �ִ�.
 * 
 ****************************************/

@Controller // ��Ʈ�ѷ���°��� ��Ÿ���� ������̼�
@RequestMapping("/board/*") // board��� ���� ��� ���Ͽ� ���� ��û�� ���� �����Ѵ�.
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService service;

	/*
	 * @RequestMapping(value = "/register", method = RequestMethod.POST) public
	 * String registPOST(BoardVO board, Model model) throws Exception {
	 * logger.info("regist post ..........."); logger.info(board.toString());
	 * 
	 * service.regist(board);
	 * 
	 * model.addAttribute("result", "success");
	 * 
	 * //return "/board/success"; return "redirect:/board/listAll"; }
	 */

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception {

		logger.info("regist post ...........");
		logger.info(board.toString());

		service.regist(board);

		rttr.addFlashAttribute("msg", "SUCCESS"); // RedirectAttributes��
													// addFlashAttribute�� �̿��ϸ�
													// uri �ڿ��ٴ� �ΰ������� ������ �ִ�.
													// ���� �׸��� "SUCCESS"���
													// "msg"�� ������ ��� �ؼ� �ϸ� �ȴ�.
		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, RedirectAttributes rttr) throws Exception {
		service.remove(bno);
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOSt(BoardVO board, RedirectAttributes rttr) throws Exception {
		logger.info("mod post...........");

		service.modify(board);
		;
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET) // register����������
																		// GET�ϴ�
																		// ��ҵ鿡
																		// ���� ����
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get ...........");
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public void listAll(Model model) throws Exception {

		logger.info("show all list...........");
		model.addAttribute("list", service.listAll());

	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(int bno, Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, Model model) throws Exception { // @RequestParam��
																					// �����Ѵ�.
		model.addAttribute(service.read(bno));
	}

	@RequestMapping(value = "/listCri", method = RequestMethod.GET)
	public void listAll(Criteria cri, Model model) throws Exception {
		logger.info("show list Page with Criteria...........");
		model.addAttribute("list", service.listCriteria(cri));
	}

	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(Criteria cri, Model model)throws Exception{
		logger.info(cri.toString());
		
		model.addAttribute("list", service.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(131);
		
		model.addAttribute("pageMaker", pageMaker);
	}
}