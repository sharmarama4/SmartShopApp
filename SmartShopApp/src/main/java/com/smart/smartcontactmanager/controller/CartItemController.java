package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.helper.Message;
import com.smart.smartcontactmanager.models.CartItem;
import com.smart.smartcontactmanager.models.Category;
import com.smart.smartcontactmanager.models.Product;
import com.smart.smartcontactmanager.models.User;
import com.smart.smartcontactmanager.repository.CartRepository;

import com.smart.smartcontactmanager.service.CartItemService;
import com.smart.smartcontactmanager.service.ProductService;
import com.smart.smartcontactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class CartItemController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;
  @Autowired
  private CartItemService cartItemService;

    @ModelAttribute()
    public void addCommonDataCart(Model model,Product product, Principal principal) {
        String name = principal.getName();
        User user = this.userService.getUserByUserName(name);
        model.addAttribute("user",user);
        System.out.println("USER: "+user);

    }



    @GetMapping("user/add-cartItem/{pId}")
    public String openAddCartItemForm(Model model,  @ModelAttribute CartItem cartItem, @PathVariable("pId") Long id) {
        model.addAttribute("title", "Add_CartItem");//to get the title
        Product foundProduct = productService.getById(id);
        cartItem.setProduct(foundProduct);

      User user=cartItem.getUser();

      Integer quantity=cartItem.getQuantity();
        Date date =cartItem.getCreatedAt();
        model.addAttribute("cartItem",cartItem);
        model.addAttribute("product", foundProduct);
        model.addAttribute("cartItems", new CartItem(quantity,date,foundProduct,user));
        return "cartItem/add_cartItem_form";
    }

   /* @GetMapping("user/add-cartItem/{pId}")
    public String openAddCartItemForm(Model model,CartItem cartItem, @PathVariable("pId") Long id) {
        model.addAttribute("title", "Add_CartItem");//to get the title
        Product product = productService.getById(id);
        cartItem.setProduct(product);
        User user = cartItem.getUser();

        Integer quantity = cartItem.getQuantity();
        Date date = cartItem.getCreatedAt();
        model.addAttribute("product", product);
        model.addAttribute("cartItems", new CartItem(quantity, date, product, user));
        return "cartItem/add_cartItem_form";
    }*/
@PostMapping("user/process-Items/{pId}")
    public String processContact( @PathVariable("pId") Long pId,@ModelAttribute CartItem cartItem2, Principal principal, HttpSession httpSession){
       User user= this.userService.getUserByUserName(principal.getName());

      Product product=this.productService.getById(pId);
       System.out.println("PEARL "+product);
      int quantity= cartItem2.getQuantity();
      Date createdAt=cartItem2.getCreatedAt();
      CartItem cartItem=new CartItem(quantity,createdAt,product,user);
      this.cartRepository.save(cartItem);
      cartItem.setUser(user);//this both line are important
      cartItem.setProduct(product);
       this.userService.save(user);
       this.productService.save(product);
       System.out.println("cartItem"+cartItem);
      System.out.println(cartItem.getProduct());
    httpSession.setAttribute("message",new Message("Your cartItem is added!! add more","success"));
    return "cartItem/add_cartItem_form";

}

  @GetMapping("user/show-cartItems/{pId}")
  public String showCartItems( @PathVariable("pId") Long pId,Model model,Principal principal){
      model.addAttribute("title", "show-cartItems");
      //  //to send list of cartItems
     /* User user=this.userService.getUserByUserName(principal.getName());
      Product product=this.productService.getById(pId);
      CartItem cartItem=new CartItem(product, user);
      List<CartItem>cartItems=this.cartRepository.findAll();
      //
      model.addAttribute("product",product);
      model .addAttribute("cartItem",cartItem);

      model.addAttribute("cartItems",cartItems);*/
      //  cartItems.forEach(p->System.out.println(""+p.getUser()+""+p.getProduct()));
      return findPaginated(1, 0l, model,principal);


  }





    /*@GetMapping("user/cart/{pageNo}/{pId}")
    public String  findPaginated(@PathVariable(value="pageNo")int pageNo,

                                 Model model,@PathVariable("pId") Long pId,Principal principal){
        int pageSize=5;
        Page<CartItem> page=cartItemService.findPaginated(pageNo,pageSize);
        List<CartItem> cartItems=page.getContent();
        User user=this.userService.getUserByUserName(principal.getName());
        Product product=this.productService.getById(pId);
        CartItem cartItem=new CartItem(product,user);
        model.addAttribute("product",product);
        model .addAttribute("cartItem",cartItem);
        //   model.addAttribute("cartItem",cartItem);

        cartItems.forEach(p->System.out.println(""+p.getUser()+""+p.getProduct()));

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("cartItems",cartItems);
        return "cartItem/show_cartItems";

    }*/
    @GetMapping("user/cart/{pageNo}/{pId}")
    public String  findPaginated(@PathVariable(value="pageNo")int pageNo,@PathVariable("pId") long pId,

                                 Model model,Principal principal){
        int pageSize=5;
        Page<CartItem> page=cartItemService.findPaginated(pageNo,pageSize);
        List<CartItem> cartItems=page.getContent();
        User user=this.userService.getUserByUserName(principal.getName());
        Product product=this.productService.getById(pId);
        CartItem cartItem=new CartItem(product,user);
        model.addAttribute("product",product);
        model .addAttribute("cartItem",cartItem);
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("cartItems",cartItems);
        return "cartItem/show_cartItems";

    }



    @GetMapping("user/delete/cartItem/id/{id}")
    public String deleteProduct(@PathVariable("id") Long id,Model model,HttpSession session) {
        Optional<CartItem> cartItemOptional = this.cartRepository.findById(id);
     CartItem cartItem = cartItemOptional.get();
        //check ..Assignment

      this.cartRepository.delete(cartItem);

        //  product.setCategory(null);//if your product is not deleted
        //if your imacge not is deleted

        session.setAttribute("message", new Message("Your cartItem removed successfully...", "success"));
        return "redirect:/user/show-cartItems/0";
    }

     @GetMapping("user/cart/uid/{id}")
    public String showCartItemsByUser( @PathVariable("id") int id, Model model) {
        model.addAttribute("title", "show-cartItems");
        //  //to send list of cartItems
        User user=this.userService.getUserById(id);
     //   Pageable pageable = PageRequest.of(page, 3);
        List<CartItem>cartItems1=this.cartRepository.findByUser(user);
      /* model.addAttribute("currentPage", page);
       model.addAttribute("totalPages", cartItems1.getTotalPages());*/
        model.addAttribute("cartItems1",cartItems1);
       return"cartItem/shopping_cart2";

}

  /*  @GetMapping("user/cart/{pageNo}/{id}")
    public String  findPaginated2(@PathVariable(value="pageNo")int pageNo,@PathVariable("id") int id,

                                 Model model,Principal principal){
        int pageSize=5;
        Page<CartItem> page=cartItemService.findPaginated2(pageNo,pageSize);
        List<CartItem> cartItems1=page.getContent();
        User user=this.userService.getUserById(id);

        CartItem cartItem=new CartItem(user);
        model.addAttribute("cartItem",cartItem);
        // cartItems.forEach(p->System.out.println(""+p.getUser()+""+p.getProduct()));

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("cartItems1",cartItems1);
        return"cartItem/shopping_cart2";
    }
*/
    @GetMapping("user/delete/cartItem/{id}")
    public String deleteItemFromUser(@PathVariable("id") Long id,Model model,HttpSession session) {
        Optional<CartItem> cartItemOptional = this.cartRepository.findById(id);
        CartItem cartItem = cartItemOptional.get();
        //check ..Assignment

        this.cartRepository.delete(cartItem);

        session.setAttribute("message", new Message("Your cartItem removed successfully...", "success"));
        return "redirect:/user/cart/uid/"+cartItem.getUser().getId()+"/0";
    }
   // update cartItem form
  @GetMapping("user/update-cartItem/{id}")
  public String updateForm(@PathVariable("id") Long id,Model model){
      model.addAttribute("tittle","Update_Item");
      CartItem cartItem=this.cartRepository.findById(id).get();
      model.addAttribute("cartItem",cartItem);
      return "cartItem/quantity_update";
  }
//this for update quantity
    @PostMapping("user/quantity/{pId}")
    public String processCartItem( @PathVariable("pId") Long pId,@ModelAttribute CartItem cartItem, Principal principal, HttpSession httpSession){
        User user= this.userService.getUserByUserName(principal.getName());
          Product product=this.productService.getById(pId);
          cartItem.setUser(user);//this both line are important
           cartItem.setProduct(product);
          this.userService.save(user);
           this.productService.save(product);
          this.cartItemService.saveCartItem(cartItem);
        System.out.println("CartItem :"+cartItem);
        System.out.println(cartItem.getProduct());
        httpSession.setAttribute("message",new Message("Your cartItem is updated Successfully!!","success"));
        return "redirect:/user/cart/uid/"+user.getId();

    }

    //this is for common data
    @GetMapping("user/cartItem/{pId}/{id}")
    public String yourProduct(Model model,@PathVariable("pId")long pId,@PathVariable("id")long id){
        model.addAttribute("title","cartItem_page");
        Product product=this.productService.getById(pId);
        CartItem cartItem=this.cartItemService.getCartItemByID(id);
        System.out.println("Product:"+product);
        System.out.println("CartItem :"+cartItem);
        model.addAttribute("product",product);
        model.addAttribute("cartItem",cartItem);
        return "cartItem/user_dashboard";


    }




}
