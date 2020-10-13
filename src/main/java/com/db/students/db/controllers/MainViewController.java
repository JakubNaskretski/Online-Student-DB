package com.db.students.db.controllers;


import com.db.students.db.entities.Student;
import com.db.students.db.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainViewController {

        @Autowired // This means to get the bean called userRepository
        private StudentRepository studentRepository;

        @RequestMapping("")
        public String addStudent(Model model){
            model.addAttribute("student", new Student());
            return "AddStudentViewTemplate";
        }

        @PostMapping("/studentAdded")
        public String greetingSubmit(@ModelAttribute Student student) {
            studentRepository.save(student);
            System.out.println("Student have been added");
            return "StudentAddedViewTemplate";
        }

        @RequestMapping(path = "/allStudents")
        public String getAllStudents(Model model) {
            model.addAttribute("students", studentRepository.findAll());
            return "AllStudentsViewTemplate";
        }

    //        Verify if it is good way to delete record from DB V2
    @PostMapping(path="/studentDeleted/{Id}")
    public String studentDeleted(@PathVariable("Id") int Id) {
        try {
            studentRepository.deleteById(Id);
        } catch (Error e ){
            e.printStackTrace();
        }
        return "StudentDeletedViewTemplate";
    }


    @RequestMapping(path="/removeAllStudents")
    public String removeAllStudents() {
        studentRepository.deleteAll();

        return "AllStudentsRemoveViewTemplate";
    }

    //    V2
    @RequestMapping(path="/allStudents/getStudent/{Id}")
    public String getStudent(@PathVariable("Id") int Id ,Model model) {
        try {
            model.addAttribute("student", studentRepository.findById(Id));
        } catch (Error e ){
            e.printStackTrace();
        }
        return "SingleStudentViewTemplate";
    }

    @PostMapping(path="/allStudents/getStudent/{Id}", params = "delete")
    public String removeStudent(@PathVariable("Id") int Id) {
        try {
            studentRepository.deleteById(Id);
        } catch (Error e ){
            e.printStackTrace();
        }
        return "StudentDeletedViewTemplate";
    }

    @PostMapping(path="/allStudents/getStudent/{Id}", params = "redirect")
    public Object removeStudent() {
        return new ModelAndView(new RedirectView("/allStudents", true));
    }

//    @RequestMapping(path = "/allStudents")
//    public String getAllStudents(Model model) {
//        model.addAttribute("students", studentRepository.findAll());
//        return "AllStudentsViewTemplate";
//    }

//    V1
//    @RequestMapping(path="/allStudents/getStudent")
//    public String removeAllStudents(Model model, @RequestParam Integer Id) {
//        try {
//            model.addAttribute("student", studentRepository.findById(Id));
//        } catch (Error e ){
//            e.printStackTrace();
//        }
//        return "SingleStudentViewTemplate";
//    }

    ////        Verify if it is good way to delete record from DB V1
//    @PostMapping(path="/studentDeleted}")
//    public String studentDeleted(@RequestParam Integer Id) {
//        try {
//            studentRepository.deleteById(Id);
//        } catch (Error e ){
//            e.printStackTrace();
//        }
//            return "StudentDeletedViewTemplate";
//        }



}