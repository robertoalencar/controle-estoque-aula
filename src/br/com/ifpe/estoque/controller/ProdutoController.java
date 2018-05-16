package br.com.ifpe.estoque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.ifpe.estoque.model.Produto;
import br.com.ifpe.estoque.model.ProdutoDao;
import br.com.ifpe.estoque.util.Util;

@Controller
public class ProdutoController {

    @RequestMapping("/produto/add")
    public String adicionarProduto() {

	return "produto/incluirProduto";
    }

    @RequestMapping("/produto/save")
    public String save(Produto produto, @RequestParam("file") MultipartFile imagem) {

	if (Util.fazerUploadImagem(imagem)) {
	    produto.setImagem(Util.obterMomentoAtual() + " - " + imagem.getOriginalFilename());
	}

	ProdutoDao dao = new ProdutoDao();
	dao.salvar(produto);
	return "produto/incluirProdutoSucesso";
    }

    @RequestMapping("/produto/list")
    public String listarProduto(Model model) {

	return "produto/listarProduto";
    }

}