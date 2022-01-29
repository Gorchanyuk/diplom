package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.CommentDTO;
import diplom.gorchanyuk.project.diplom.entity.Comment;
import diplom.gorchanyuk.project.diplom.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public List<CommentDTO> findAll() {
        List<Comment> comments = commentRepository.findAll(Sort.by(Sort.Order.asc("dateAdded")));
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
    }

    public CommentDTO findById(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(comment, CommentDTO.class);
    }


    @Transactional
    public boolean save( CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getId()).orElse(null);
        if (comment != null) return false;

        comment = modelMapper.map(commentDTO, Comment.class);
        comment.setDateAdded(new Date());
        commentRepository.save(comment);
        return true;
    }

    public boolean delete(long id){
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
