package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

  private final ItemRepository itemRepository;

  @GetMapping
  public String items(Model model) {
    List<Item> items = itemRepository.findAll();
    model.addAttribute("items", items);
    return "basic/items";
  }

  @GetMapping("/{itemId}")
  public String item(@PathVariable("itemId") long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/item";
  }

  @GetMapping("/add")
  public String addForm() {
    return "basic/addForm";
  }

//  @PostMapping("/add")
//  public String addItemV1(
//      @RequestParam("itemName") String itemName,
//      @RequestParam("price") int price,
//      @RequestParam("quantity") Integer quantity,
//      Model model) {
//
//    Item item = new Item();
//    item.setItemName(itemName);
//    item.setPrice(price);
//    item.setQuantity(quantity);
//
//    itemRepository.save(item);
//    model.addAttribute("item", item);
//
//    return "basic/item";
//  }

//  @PostMapping("/add")
//  public String addItemV2(@ModelAttribute("item") Item item) {
//
//    itemRepository.save(item);
//    return "basic/item";
//  }

//  @PostMapping("/add")
//  public String addItemV3(@ModelAttribute Item item) {
//    itemRepository.save(item);
//    return "basic/item";
//  }

//  @PostMapping("/add")
//  public String addItemV4(Item item) {
//    itemRepository.save(item);
//    return "basic/item";
//  }

  @PostMapping("/add")
  public String addItemV5(Item item) {
    itemRepository.save(item);
    return "redirect:/basic/items/" + item.getId();
  }

  @GetMapping("/{itemId}/edit")
  public String editForm(@PathVariable("itemId") Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "basic/editForm";
  }

  @PostMapping("/{itemId}/edit")
  public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
    itemRepository.update(itemId, item);
    return "redirect:/basic/items/{itemId}";
  }

  @PostConstruct
  public void init() {
    itemRepository.save(new Item("itemA", 10000, 100));
    itemRepository.save(new Item("itemB", 20000, 200));
  }

}
