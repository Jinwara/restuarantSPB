package th.or.orcsofts.training.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import th.or.orcsofts.training.entity.*;
import th.or.orcsofts.training.model.request.*;
import th.or.orcsofts.training.model.response.MenuResponse;
import th.or.orcsofts.training.model.response.OrderResponse;
import th.or.orcsofts.training.model.response.ProductResponse;
import th.or.orcsofts.training.service.RestuarantService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestuarantController {
    private final RestuarantService restuarantService;

    public RestuarantController(RestuarantService ingredientService) {
        this.restuarantService = ingredientService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/menu/create")
    public ResponseEntity<Object> createMenu(@RequestBody MenuRequest request) {
        return restuarantService.createMenu(request);
    }

    @PostMapping ("/menu/{id}/ingredients")
    public ResponseEntity<Object> createIngredient(@PathVariable Integer id, @RequestBody IngredientRequest request) throws Exception {
        return restuarantService.createIngredient(id,request);
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestBody ProductRequest request) {
        return restuarantService.createProduct(request);
    }

    @GetMapping("/menu/{id}")
    public MenuResponse retrieveMenu(@PathVariable int id) throws Exception  {
        return restuarantService.getMenu(id);
    }

    @GetMapping("/menus")
    public List<MenuEntity> getMenutList() {
        return restuarantService.getMenutList();
    }

    @GetMapping("/products")
    public List<ProductEntity> getProductList() {
        return restuarantService.getProductList();
    }

    @GetMapping("/menu/{id}/ingredients")
    public List<IngredientEntity> retriveAllMenus(@PathVariable Integer id){
        return restuarantService.getIngredientList(id);
    }

    @GetMapping("/product/{id}")
    public ProductResponse retrieveProduct(@PathVariable int id) throws Exception  {
        return restuarantService.getProduct(id);
    }

    @DeleteMapping("/menu/{id}")
    public void deleteMenu(@PathVariable int id) {
        restuarantService.deleteMenu(id);
    }

    @DeleteMapping("/ingredient/{id}")
    public void deleteIngredient(@PathVariable int id) {
        restuarantService.deleteIngredient(id);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        restuarantService.deleteProduct(id);
    }

    @PutMapping("/menu/{id}")
    public ResponseEntity<Object> editMenu(@PathVariable Integer id, @RequestBody MenuRequest request) {
        return restuarantService.editMenu(id,request);
    }

    @PutMapping("/ingredient/{id}")
    public ResponseEntity<Object> editIngredient(@PathVariable Integer id, @RequestBody IngredientRequest request) {
        return restuarantService.editIngredient(id,request);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> editProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
        return restuarantService.editProduct(id,request);
    }

    @GetMapping("/product/search")
    public List<ProductEntity> viewHomePage(Model model, @Param("keyword") String keyword) {
        List<ProductEntity> listProducts = restuarantService.listProductAll(keyword);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("keyword", keyword);
        return listProducts;
    }

    @PostMapping("menu/uploadimage/{id}")
    public Map<String, Object>  storeMenuImage(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        MenuEntity fileStorage = restuarantService.storeMenuImage(id,file);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(fileStorage.getId().toString())
                .toUriString();
        Map<String, Object> fileMapping = new HashMap<>();
        fileMapping.put("fileName", fileStorage.getImage());
        fileMapping.put("uri", fileDownloadUri);
        fileMapping.put("type", file.getContentType());
        fileMapping.put("size", file.getSize());
        return fileMapping;
    }

    @PostMapping("product/uploadimage/{id}")
    public Map<String, Object>  storeProductImage(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        ProductEntity fileStorage = restuarantService.storeProductImage(id,file);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(fileStorage.getId().toString())
                .toUriString();
        Map<String, Object> fileMapping = new HashMap<>();
        fileMapping.put("fileName", fileStorage.getImage());
        fileMapping.put("uri", fileDownloadUri);
        fileMapping.put("type", file.getContentType());
        fileMapping.put("size", file.getSize());
        return fileMapping;
    }

    @GetMapping("menu/preview/{id}")
    public ResponseEntity<?> previewMenuImage(@PathVariable Integer id) {
        return restuarantService.previewMenuImage(id);
    }

    @GetMapping("menu/download/{id}")
    public ResponseEntity<?> downloadMenuImage(@PathVariable Integer id) {
        return restuarantService.downloadMenuImage(id);
    }

    @GetMapping("product/download/{id}")
    public ResponseEntity<?> downloadProductImage(@PathVariable Integer id) {
        return restuarantService.downloadProductImage(id);
    }

    @PostMapping ("/order/{id}/orderitems")
    public ResponseEntity<Object> createOrderItem(@PathVariable Integer id, @RequestBody OrderItemRequest request) throws Exception {
        return restuarantService.createOrderItem(id,request);
    }

    @PostMapping("/order/create")
    public String createOrder(@RequestBody OrderRequest request) {
        return restuarantService.createOrder(request);
    }

    @GetMapping("/order/{id}/orderitems")
    public List<OrderItemEntity> getOrderList(@PathVariable Integer id){
        return restuarantService.getOrderList(id);
    }

    @GetMapping("/orders")
    public List<OrderEntity> getAllOrderList() {
        return restuarantService.getAllOrderList();
    }

    @GetMapping("/order/{id}")
    public OrderResponse getOrder(@PathVariable int id) throws Exception  {
        return restuarantService.getOrder(id);
    }

}
