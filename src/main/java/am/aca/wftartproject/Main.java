package am.aca.wftartproject;

import am.aca.wftartproject.controller.CtxListener;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.ShoppingCardType;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;

import java.sql.SQLException;

/**
 * Created by ASUS on 24-May-17
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {


////        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-root.xml");
////        ItemService itemService = (ItemService)context.getBean("itemService");
////        for(Item element:itemService.getRecentlyAddedItems(20)){
//            ArtistSpecialization artistSpecialization = ArtistSpecialization.valueOf("SCULPTOR");
//          System.out.println(artistSpecialization);
//        System.out.println(System.getProperty("java.io.tmpdir"));
//
////
////        }
//
//
//        int num = ThreadLocalRandom.current().nextInt(1000, 100000 + 1);
//        System.out.println(num);
//
////        ArtistSpecialization[] art = ArtistSpecialization.values();
////        for(ArtistSpecialization artElement:art){
////            System.out.println(artElement.getType());
////        }
//
//
//        Item item1 = new Item("Title 1","bbbbb","asasasa",1000.0,1L,true, ItemType.SCULPTURE,new Timestamp(12312312423432L));
//        Item item2 = new Item("Title 2","bbbbb","asasasa",5000.0,2L,false, ItemType.SCULPTURE,new Timestamp(12312312423432L));
//        Item item3 = new Item("Title 3","bbbbb","asasasa",3000.0,3L,true, ItemType.SCULPTURE,new Timestamp(12312312423432L));
//        Item item4 = new Item("Title 4","bbbbb","asasasa",10000.0,4L,true, ItemType.SCULPTURE,new Timestamp(12312312423432L));
//
//        List<Item> itemList = new ArrayList<>();
//        itemList.add(item1);
//        itemList.add(item2);
//        itemList.add(item3);
//        itemList.add(item4);
//
//        Collections.sort(itemList, ItemComparator.getComparator(ItemComparator.PRICE_SORT));
//        for(Item option:itemList){
//            System.out.println("option.getId() + option.toString() = " + option.getId() + option.toString());
//        }
//        System.out.println();
//
//
//        Collections.sort(itemList, ItemComparator.descending(ItemComparator.getComparator(ItemComparator.TITLE_SORT)));
//        for(Item option:itemList){
//            System.out.println("option.getId() + option.toString() = " + option.getId() + option.toString());
//        }
//        System.out.println();
//
//
//        Collections.sort(itemList, ItemComparator.getComparator(ItemComparator.AVAILABILITY_SORT));
//        for(Item option:itemList){
//            System.out.println("option.getId() + option.toString() = " + option.getId() + option.toString());
//        }
//        System.out.println();
//
//
//        Collections.sort(itemList, ItemComparator.descending(ItemComparator.getComparator(ItemComparator.AVAILABILITY_SORT)));
//        for(Item option:itemList){
//            System.out.println("option.getId() + option.toString() = " + option.getId() + option.toString());
//        }

        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE, UserServiceImpl.class);
        User user = new User("vahan","bakaryan",34,"vahan.bakaryan@inbox.com","Test123456",new ShoppingCard(ShoppingCardType.MASTERCARD));
    }
}
