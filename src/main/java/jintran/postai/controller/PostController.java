package jintran.postai.controller;

import jintran.postai.Uri.PostRequestUri;
import jintran.postai.Uri.PostViewUri;
import jintran.postai.model.category.Category;
import jintran.postai.model.post.Post;
import jintran.postai.model.post.PostDTO;
import jintran.postai.service.ICategoryService;
import jintran.postai.service.IPostService;
import jintran.postai.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RequestMapping(PostRequestUri.POST)
@Controller
public class PostController {
    public static final String IMAGE_FILE_INVALID_MESSAGE = "You need to add an image file";
    public static final int PAGE_NUMBER_TO_PRESENT = 5;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private ICategoryService iCategoryService;

    @ModelAttribute("categories")
    public Iterable<Category> listCategory() {
        return iCategoryService.findAll();
        @GetMapping({PostRequestUri.BLANK, PostRequestUri.SLASH})
        public ModelAndView showPost (ModelAndView
        modelAndView, @PageableDefault(value = PAGE_NUMBER_TO_PRESENT) Pageable pageable){
            Page<Post> post = iPostService.findAll(pageable);
            modelAndView.addObject("post", post);
            modelAndView.setViewName(PostViewUri.Post_INDEX);
            return modelAndView;
        }

        @GetMapping(PostRequestUri.CREATE)
        public ModelAndView showCreateForm (ModelAndView modelAndView, Model model){
            PostDTO postDTO = new PostDTO();
            modelAndView.addObject("postDTO", postDTO);
            modelAndView.setViewName(PostViewUri.Post_CREATE);
            return modelAndView;
        }
        @PostMapping(PostRequestUri.CREATE)
        public String createPost (@Valid @ModelAttribute("postDTO") PostDTO postDTO, BindingResult bindingResult){
            iPostService.checkUploadImageInvalid(postDTO, bindingResult);
            if (isBindingError(bindingResult)) return PostViewUri.Post_CREATE;
            String fileName = iPostService.getFileName(postDTO);
            try {
                iPostService.saveAnCopyOfFileToStorage(postDTO, fileName);
                iPostService.savePost(postDTO, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return PostViewUri.REDIRECT_TO_Post;
        }


        @GetMapping("/{id}/edit")
        public String showEditPage (Model model, @PathVariable Long id){
            Optional<Post> post = iPostService.findById(id);
            model.addAttribute("post", post.get());
            PostDTO postDTO = new PostDTO();
            postDTO.setTitle(post.get().getTitle());
            postDTO.setContent(post.get().getContent());
            postDTO.setShortDescription(post.get().getShortDescription());
            postDTO.setCategory(post.get().getCategory());
            model.addAttribute("postDTO", postDTO);
            return PostViewUri.Post_EDIT;
        }

        private static PostDTO getPostDTO (Post post){
            PostDTO postDTO = new PostDTO();
            postDTO.setTitle(post.getTitle());
            postDTO.setContent(post.getContent());
            postDTO.setShortDescription(post.getShortDescription());
            postDTO.setCategory(post.getCategory());

            return postDTO;
        }

        @PostMapping("/{id}/edit")
        public String editPost (@Valid @ModelAttribute PostDTO postDTO, BindingResult bindingResult, @PathVariable Long
        id){

            iPostService.checkUploadImageInvalid(postDTO, bindingResult);
            if (isBindingError(bindingResult)) return PostViewUri.Post_EDIT;
            Post post = iPostService.findById(id).get();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setShortDescription(postDTO.getShortDescription());
            iPostService.save(post);
            return PostViewUri.REDIRECT_TO_Post;
        }

        @GetMapping(PostRequestUri.DELETE)
        public String showDeletePage (@PathVariable Long id){
            iPostService.remove(id);
        /*TODO: remove xong trong CSDL -
           > delete ca anh co trong storage/images*/
            return PostViewUri.REDIRECT_TO_Post;
        }

        @GetMapping("/search")
        public ModelAndView listPost
        (@RequestParam("search") Optional < String > search, @PageableDefault(value = PAGE_NUMBER_TO_PRESENT) Pageable
        pageable){
            Page<Post> post;
            if (search.isPresent()) {
                post = iPostService.findAllByTitle(pageable, search.get());
            } else {
                post = iPostService.findAll(pageable);
            }
            ModelAndView modelAndView = new ModelAndView("/post/index");
            modelAndView.addObject("post", post);
            return modelAndView;
        }

        private static boolean isBindingError (BindingResult bindingResult){
            return bindingResult.hasErrors();
        }
    }
}