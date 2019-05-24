package carschallenge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    CatMakeRepository catMakeRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CloudinaryClass cloudinaryClass;

    @RequestMapping("/") //This page will display all the cars uploaded by clients
    public String home(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "index";
    }

    // This section is for all cars being made.
    @GetMapping("/AddCar")
    public String addACar(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("category", CatMakeRepository.findAll());
        return "carForm";
    }

    @PostMapping("/processCar") // <-- not sure if i need this part
    public String processCarForm(@ModelAttribute Car cars, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/";
        }
        try {
            Map uploadResult = cloudinaryClass.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));

            cars.setPicture(uploadResult.get("url").toString());
            carRepository.save(cars);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/";
    }

    @RequestMapping("/detail/car/{id}")
    public String showCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        return "mycar";
    }

    @RequestMapping("/update/car/{id}")
    public String updateCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        model.addAttribute("category", catMakeRepository.findAll());
        return "carForm";
    }

    @RequestMapping("/delete/car/{id}")
    public String delCar(@PathVariable("id") long id) {
        carRepository.deleteById(id);
        return "redirect:/";
    }

    // End of the car section.

    //Start of the category

    @GetMapping("/addCategory")
    public String makeACategory(Model model) {
        model.addAttribute("Catmake", new CatMake());
        return "addCategory";
    }

    @PostMapping("/process")
    public String processForm(@Valid CatMake catMake, BindingResult result) {
        if (result.hasErrors()) {
            return "addCategory";
        }
        CatMakeRepository.save(category);
        return "redirect:/";
    }
}