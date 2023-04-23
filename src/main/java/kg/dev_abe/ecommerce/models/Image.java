package kg.dev_abe.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    private static final String SEQ_NAME = "image_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 10)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToOne
    private Category category;
    @Lob
    private byte[] imageData;

    private String fileType;

    public Image(byte[] imageData, String fileType) {
        this.imageData = imageData;
        this.fileType = fileType;
    }
    public static Image parseImage(MultipartFile file) {
        Image image = new Image();
        try {
            image.setImageData(file.getBytes());
            image.setFileType(file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
