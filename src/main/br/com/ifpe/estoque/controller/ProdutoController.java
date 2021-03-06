package main.br.com.ifpe.estoque.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import main.br.com.ifpe.estoque.model.CategoriaProduto;
import main.br.com.ifpe.estoque.model.CategoriaProdutoDao;
import main.br.com.ifpe.estoque.model.Produto;
import main.br.com.ifpe.estoque.model.ProdutoDao;
import main.br.com.ifpe.estoque.util.Util;

@Controller
public class ProdutoController {

    @RequestMapping("/produto/add")
    public String adicionarProduto(Model model) {

	// Código para popular o combo de categoria de produto
	CategoriaProdutoDao dao = new CategoriaProdutoDao();
	List<CategoriaProduto> listaCategoriaProduto = dao.listar(null);
	model.addAttribute("listaCategoriaProduto", listaCategoriaProduto);

	return "produto/incluirProduto";
    }

    @RequestMapping("/produto/save")
    public String save(Produto produto, @RequestParam("file") MultipartFile imagem, Model model) {

	if (Util.fazerUploadImagem(imagem)) {
	    produto.setImagem(Util.obterMomentoAtual() + " - " + imagem.getOriginalFilename());
	}

	ProdutoDao dao = new ProdutoDao();
	dao.salvar(produto);

	model.addAttribute("mensagem", "Produto Incluído com Sucesso");
	return "produto/incluirProduto";
    }

    @RequestMapping("/produto/list")
    public String listarProduto(Model model) {

	ProdutoDao dao = new ProdutoDao();
	List<Produto> listaProduto = dao.listar(null, null);
	model.addAttribute("listaProduto", listaProduto);
	
	model.addAttribute("listaCategoriaProduto", new CategoriaProdutoDao().listar(null));

	return "produto/listarProduto";
    }

    @RequestMapping(value = "/produto/filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String filtrarProduto(@RequestParam String descricao, @RequestParam Integer idCategoria) {

	ProdutoDao dao = new ProdutoDao();
	List<Produto> listaProduto = dao.listar(descricao, idCategoria);

	return new Gson().toJson(listaProduto);
    }

    @RequestMapping("/produto/edit")
    public String edit(@RequestParam("id") Integer id, Model model) {

	ProdutoDao dao = new ProdutoDao();
	Produto produto = dao.buscarPorId(id);
	model.addAttribute("produto", produto);

	return "produto/alterarProduto";
    }

    @RequestMapping("/produto/update")
    public String update(Produto produto, Model model) {

	ProdutoDao dao = new ProdutoDao();
	dao.alterar(produto);
	model.addAttribute("mensagem", "Produto Alterado com Sucesso !");

	return "forward:list";
    }

    @RequestMapping("/produto/delete")
    public String delete(@RequestParam("id") Integer id, Model model) {

	ProdutoDao dao = new ProdutoDao();
	dao.remover(id);
	model.addAttribute("mensagem", "Produto Removido com Sucesso");

	return "forward:list";
    }

    @RequestMapping("/produto/view")
    public String view(@RequestParam("id") Integer id, Model model) {

	ProdutoDao dao = new ProdutoDao();
	Produto produto = dao.buscarPorId(id);
	model.addAttribute("produto", produto);

	return "produto/exibirProduto";
    }
}