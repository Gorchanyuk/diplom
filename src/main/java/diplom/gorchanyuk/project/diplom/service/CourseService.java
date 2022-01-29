package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.entity.Course;
import diplom.gorchanyuk.project.diplom.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Cacheable(cacheNames = "courses")
    @Transactional
    public List<CourseDTO> findAll() {
        List<Course> courses = courseRepository.findAll();
        courses.sort((o1, o2) -> {
            Integer a = o1.getChildren().size();
            Integer b = o2.getChildren().size();
            return b.compareTo(a);
        });

        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    public CourseDTO findById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(course, CourseDTO.class);
    }

//    public CourseDTO findByName(String name){
//        Course course = courseRepository.findByNameCourse(name)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        return modelMapper.map(course, CourseDTO.class);
//    }

    public CourseDTO findBySlug(String name){
        Course course = courseRepository.findBySlug(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(course, CourseDTO.class);
    }

    @Transactional
    public boolean save(CourseDTO courseDTO) {
        Course course = courseRepository.findByNameCourse(courseDTO.getNameCourse()).orElse(null);
        if (course != null && course.getNameCourse().equalsIgnoreCase(courseDTO.getNameCourse())) return false;

        course = modelMapper.map(courseDTO, Course.class);
        course.setDateAdded(new Date());
        courseRepository.save(course);
        return true;
    }

    public boolean delete(long id){
        if (courseRepository.findById(id).isPresent()) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
