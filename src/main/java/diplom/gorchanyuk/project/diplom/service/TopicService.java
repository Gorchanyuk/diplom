package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.config.Transcriptor;
import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.dto.TopicDTO;
import diplom.gorchanyuk.project.diplom.entity.Course;
import diplom.gorchanyuk.project.diplom.entity.Topic;
import diplom.gorchanyuk.project.diplom.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;
    private final CourseService courseService;
    private final EntityManager entityManager;

    @Transactional
    public List<TopicDTO> findAll() {
        List<Topic> topics = topicRepository.findAll();

        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicDTO.class))
                .collect(Collectors.toList());
    }

    public List<TopicDTO> findByCourse(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        List<Topic> topics = topicRepository.findAllByCourseId(course);

        return topics.stream()
                .map(topic -> modelMapper.map(topic, TopicDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Topic save(TopicDTO topicDTO) {

        Topic topic = modelMapper.map(topicDTO, Topic.class);

        Course course;
        if (topic.getCourseId().getId() == 0) {
            course = courseService.save(topicDTO.getCourseId());
        }else{
            course = entityManager.getReference(Course.class, topic.getCourseId().getId());
        }
        topic.setCourseId(course);
        topic.setDateAdded(new Date());
        topic.setSlug(Transcriptor.transcript(topicDTO.getNameTopic()));
        topicRepository.save(topic);
        return topic;
    }
}
