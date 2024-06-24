package jintran.postai.model.post;

import jintran.postai.model.category.Category;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class PostDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "TEXT")
    private String shortDescription;
    private String imageFileName;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String content, String shortDescription, String imageFileName, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.shortDescription = shortDescription;
        this.imageFileName = imageFileName;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
