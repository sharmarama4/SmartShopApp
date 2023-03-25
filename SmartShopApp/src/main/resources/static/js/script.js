console.log("This is my first script file");
const toggleSidebar=()=>{
    if($('.sidebar').is(":visible")){
        //true
        //banda karna hai
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");
    }else{
        //false
        //show karna hai
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
    }
};
const searchContact=()=>{
    // console.log("searching...");  
    let query=$("#search-input").val();
    console.log(query);
    if(query==""){
       $(".search-result").hide();
   
    }else{
       console.log(query);
       //sending request to server
   
       let url = `http://localhost:8282/search/${query}`;
   
       fetch(url).then((response)=>{
           return response.json();
       }).then((data)=>{
           //data
          // console.log(data);
           let text=`<div class='list-group'>`;
           data.forEach((contact) => {
               text+=`<a href='/user/${contact.cId}/contact' class='list-group-item list-group-item-action'>${contact.name}</a>`
               
           });
           text+=`</div>`;
           $(".search-result").html(text);
           $(".search-result").show();
       });  
    }
   };
  
    //search for product
    const searchProduct=()=>{
        // console.log("searching...");  
        let query=$("#search-input1").val();
        console.log(query1);
        if(query1==""){
           $(".search-result1").hide();
       
        }else{
           console.log(query1);
           //sending request to server
       
           let url = `http://localhost:8282/search/product/${query}/${category.categoryName}`;
       
           fetch(url).then((response)=>{
               return response.json();
           }).then((data)=>{
               //data
             //  console.log(data);
               let text=`<div class='list-group'>`;
               data.forEach((product) => {
                   text+=`<a href='/user/${product.pId}/product' class='list-group-item list-group-item-action'>${product.productName}</a>`
                  
               });
               text+=`</div>`;
               $(".search-result1").html(text);
               $(".search-result1").show();
           });
           
          
        }
       };
