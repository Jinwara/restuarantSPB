package th.or.orcsofts.training.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import th.or.orcsofts.training.entity.*;
import th.or.orcsofts.training.exception.NotFoundException;
import th.or.orcsofts.training.mapper.MenuMapper;
import th.or.orcsofts.training.mapper.OrderMapper;
import th.or.orcsofts.training.mapper.ProductMapper;
import th.or.orcsofts.training.model.request.*;
import th.or.orcsofts.training.model.response.MenuResponse;
import th.or.orcsofts.training.model.response.OrderResponse;
import th.or.orcsofts.training.model.response.ProductResponse;
import th.or.orcsofts.training.repository.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class RestuarantService {
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public RestuarantService(IngredientRepository ingredientRepository, ProductRepository productRepository, MenuRepository menuRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.ingredientRepository = ingredientRepository;
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public ResponseEntity<Object> createMenu(MenuRequest request) {

        MenuEntity pe = new MenuEntity();

        pe.setMenuName(request.getMenuName());
        pe.setIngredientList(request.getIngredientList());
        pe.setSellingPrice(request.getSellingPrice());

        MenuEntity result = menuRepository.save(pe);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<Object> createIngredient(Integer id, IngredientRequest request) throws Exception {

        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }

        MenuEntity  menu = menuEntityOptional.get();

        Optional<ProductEntity> productEntityOptional = productRepository.findById(request.getProductID());
        if(!productEntityOptional.isPresent()) {
            throw new NotFoundException("product id - " + request.getProductID());
        }

        ProductEntity  product = productEntityOptional.get();

        IngredientEntity pe = new IngredientEntity();

        pe.setIngredientName(request.getIngredientName());
        pe.setQuantity(request.getQuantity());
        pe.setUnitType(getProduct(request.getProductID()).getUnitType());
        pe.setCost( getProduct(request.getProductID()).getUnitCost());
        pe.setTotalCost(Double.valueOf(request.getQuantity())* getProduct(request.getProductID()).getUnitCost());
        pe.setMenuID(menu);
        pe.setProductID(product);

        ingredientRepository.save(pe);

        updatePriceMenu(id);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(menu.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    private void updatePriceMenu(Integer id) {
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }

        MenuEntity  menu = menuEntityOptional.get();

        Double totalCost =  0.0;
        for (IngredientEntity ingredient : menu.getIngredientList()) {
            totalCost  = ingredient.getTotalCost()+totalCost;
        }
        menu.setCost(totalCost);
        menu.setProfit(menu.getSellingPrice()-totalCost);
        menuRepository.save(menu);
    }

    public String createProduct(ProductRequest request) {
        ProductEntity pe = new ProductEntity();

        pe.setProductName(request.getProductName());
        pe.setBrand(request.getBrand());
        pe.setUnitType(request.getUnitType());
        pe.setCost(request.getCost());
        pe.setProductWeight(request.getProductWeight());
        pe.setUnitCost(request.getCost()/request.getProductWeight());

        ProductEntity result = productRepository.save(pe);

        if (ObjectUtils.isEmpty(result)) {
            return "Create product failed.";
        }

        return "Create product success." +result.getProductName()+" "+result.getUnitType()
                +" unit cost : "+result.getUnitCost();
    }

    public MenuResponse getMenu(int id) throws Exception {
        MenuEntity me = menuRepository.findById(id)
                .orElseThrow(() -> new Exception("Menu not found"));

        Double totalcost = 0.0;

        for (IngredientEntity element : me.getIngredientList()) {
            totalcost = totalcost + element.getCost();
        }


        return MenuMapper.INSTANCE.entityToResponse(me);
    }

    public List<IngredientEntity> getIngredientList(Integer id){
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - "+ id);
        }
        return menuEntityOptional.get().getIngredientList();
    }

    public List<MenuEntity> getMenutList(){
        return menuRepository.findAll();
    }

    public List<ProductEntity> getProductList(){
        return productRepository.findAll();
    }

    public ProductResponse getProduct(Integer id) throws Exception {
        ProductEntity pe = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found"));
        return ProductMapper.INSTANCE.entityToResponse(pe);
    }

    public void deleteMenu(int id) {
        List<IngredientEntity> listIngredient = getIngredientList(id);

        for (IngredientEntity element : listIngredient) {
            ingredientRepository.deleteById(element.getId());
        }

        menuRepository.deleteById(id);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void deleteIngredient(int id) {
        ingredientRepository.deleteById(id);
    }

    public ResponseEntity<Object>  editMenu(Integer  id,MenuRequest request) {
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }

        MenuEntity  menu = menuEntityOptional.get();

        Double totalCost =  0.0;
        for (IngredientEntity ingredient : menu.getIngredientList()) {
            totalCost  = ingredient.getTotalCost()+totalCost;
        }
        menu.setCost(totalCost);
        menu.setProfit(menu.getSellingPrice()-totalCost);

        if(request.getMenuName()!=null){
            menu.setMenuName(request.getMenuName());
        }
        if(request.getSellingPrice()!=null){
            menu.setSellingPrice(request.getSellingPrice());
        }
        if(request.getIngredientList()!=null){
            menu.setIngredientList(request.getIngredientList());
        }

        MenuEntity updatedMenu = menuRepository.save(menu);

        return ResponseEntity.ok(updatedMenu);
    }

    public ResponseEntity<Object> editIngredient(Integer id, IngredientRequest request) {
        Optional<IngredientEntity> ingredientEntityOptional = ingredientRepository.findById(id);
        if(!ingredientEntityOptional.isPresent()) {
            throw new NotFoundException("ingredient id -" + id);
        }

        IngredientEntity  ingredient = ingredientEntityOptional.get();

        if(request.getIngredientName()!=null){
            ingredient.setIngredientName(request.getIngredientName());
        }

        if(request.getQuantity()!=null){
            ingredient.setQuantity(request.getQuantity());
            ingredient.setTotalCost(Double.valueOf(request.getQuantity())* ingredient.getCost());
        }

        IngredientEntity updatedIngredient = ingredientRepository.save(ingredient);

        return ResponseEntity.ok(updatedIngredient);
    }

    public ResponseEntity<Object> editProduct(Integer id, ProductRequest request) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        if(!productEntityOptional.isPresent()) {
            throw new NotFoundException("product id -" + id);
        }

        ProductEntity  product = productEntityOptional.get();

        if(request.getProductName()!=null){
            product.setProductName(request.getProductName());
        }

        if(request.getProductWeight()!=null && request.getCost()!=null){
            product.setProductWeight(request.getProductWeight());
            product.setCost(request.getCost());
            product.setUnitCost(request.getCost()/request.getProductWeight());
        } else if(request.getProductWeight()==null && request.getCost()!=null) {
            product.setCost(request.getCost());
            product.setUnitCost(request.getCost()/product.getProductWeight());
        } else if(request.getProductWeight()!=null && request.getCost()==null) {
            product.setProductWeight(request.getProductWeight());
            product.setUnitCost(product.getCost()/request.getProductWeight());
        }

        if(request.getBrand()!=null){
            product.setBrand(request.getBrand());
        }

        if(request.getUnitType()!=null){
            product.setUnitType(request.getUnitType());
        }

        ProductEntity updatedProduct = productRepository.save(product);

        return ResponseEntity.ok(updatedProduct);
    }

    public List<ProductEntity> listProductAll(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return productRepository.findAll();
    }

    public MenuEntity storeMenuImage(Integer id, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) return null;
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }

        MenuEntity  menu = menuEntityOptional.get();

        menu.setImage(fileName);
        menu.setImageType(file.getContentType());
        menu.setImagedata(file.getBytes());

        return menuRepository.save(menu);
    }

    public ProductEntity storeProductImage(Integer id, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) return null;
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        if(!productEntityOptional.isPresent()) {
            throw new NotFoundException("product id - " + id);
        }

        ProductEntity  product = productEntityOptional.get();

        product.setImage(fileName);
        product.setImageType(file.getContentType());
        product.setImagedata(file.getBytes());

        return productRepository.save(product);
    }

    public ResponseEntity<InputStreamResource> previewMenuImage(Integer id) {
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }

        MenuEntity  menu = menuEntityOptional.get();
        return ResponseEntity.ok()
                .contentType
                        (MediaType.parseMediaType(menu.getImageType()))
                .body(new InputStreamResource(new ByteArrayInputStream(menu.getImagedata())));
    }

    public ResponseEntity<?> downloadMenuImage(Integer id) {
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }
        MenuEntity  menu = menuEntityOptional.get();
        return ResponseEntity.ok()
                .contentType
                        (MediaType.parseMediaType(menu.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + menu.getImage() + "\"")
                .body(new ByteArrayResource(menu.getImagedata()));
    }

    public ResponseEntity<?> downloadProductImage(Integer id) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        if(!productEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }
        ProductEntity  product = productEntityOptional.get();
        return ResponseEntity.ok()
                .contentType
                        (MediaType.parseMediaType(product.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + product.getImage() + "\"")
                .body(new ByteArrayResource(product.getImagedata()));
    }

    public ResponseEntity<Object> createOrderItem(Integer id, OrderItemRequest request) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if(!orderEntityOptional.isPresent()) {
            throw new NotFoundException("order id - " + id);
        }

        OrderEntity  order = orderEntityOptional.get();

        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(request.getMenuID());
        if(!menuEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }

        MenuEntity  menu = menuEntityOptional.get();

        OrderItemEntity orderItem = new OrderItemEntity();

        orderItem.setQuantity(request.getQuantity());
        orderItem.setMenuID(menu);
        orderItem.setMenuName(menu.getMenuName());
        orderItem.setTotalPrice(menu.getSellingPrice()*request.getQuantity());
        orderItem.setOrderID(order);
        orderItem.setUnitPrice(menu.getSellingPrice());

        updatePriceOrder(id);

        orderItemRepository.save(orderItem);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderItem.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public String createOrder(OrderRequest request) {

        OrderEntity order = new OrderEntity();

        order.setDate(request.getDate());
        order.setTotalCost(0.0);
        order.setTotalPrice(0.0);
        order.setTotalProfit(0.0);
        order.setOrderitemList(request.getOrderitemList());
        OrderEntity result = orderRepository.save(order);

        if (ObjectUtils.isEmpty(result)) {
            return "Create order failed.";
        }

        return "Create order success. Order ID :" +result.getId();
    }

    public List<OrderItemEntity> getOrderList(Integer id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if(!orderEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - "+ id);
        }
        return orderEntityOptional.get().getOrderitemList();
    }

    private void updatePriceOrder(Integer id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if(!orderEntityOptional.isPresent()) {
            throw new NotFoundException("menu id - " + id);
        }

        OrderEntity  order = orderEntityOptional.get();

        Double totalPrice =  0.0;
        for (OrderItemEntity orderElement : order.getOrderitemList()) {
            totalPrice  = orderElement.getTotalPrice()+totalPrice;
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
    }

    public List<OrderEntity> getAllOrderList() { return orderRepository.findAll();}

    public OrderResponse getOrder(int id) throws Exception {

        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Order not found"));

        Double totalPrice = 0.0;

        for (OrderItemEntity element : orderEntity.getOrderitemList()) {
            totalPrice = totalPrice + element.getTotalPrice();
        }

        return OrderMapper.INSTANCE.entityToResponse(orderEntity);

    }
}
