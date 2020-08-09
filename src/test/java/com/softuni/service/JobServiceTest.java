package com.softuni.service;

import com.softuni.model.entity.Enum.Region;
import com.softuni.model.entity.Enum.jobEnum.JobLevel;
import com.softuni.model.entity.Enum.jobEnum.JobType;
import com.softuni.model.entity.Enum.vehicleEnum.Engine;
import com.softuni.model.entity.Enum.vehicleEnum.Transmission;
import com.softuni.model.entity.Job;
import com.softuni.model.entity.User;
import com.softuni.model.entity.Vehicle;
import com.softuni.model.service.JobServiceModel;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.repository.JobRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JobServiceTest {
    @MockBean
    UserService userService;

    @Autowired
    JobService jobService;

    @MockBean
    JobRepository jobRepository;

    Job job;
    User user;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);

        job = new Job();
        job.setJobLevel(JobLevel.Employees);
        job.setId("asd");
        job.setRegion(Region.Burgas);
        job.setJobType(JobType.TemporaryJob);
        user = new User();
        user.setUsername("test");
        job.setUser(user);
    }

    @Test
    public void shouldReturnJobById(){
        Mockito.when(this.jobRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(job));
        JobServiceModel expected = jobService.findById("asd", anyString());
        Assert.assertEquals(expected.getJobLevel(), job.getJobLevel().name());
        Assert.assertEquals(expected.getJobType(), job.getJobType().name());
    }

    @Test
    public void shouldReturnAllJobs(){
        List<Job> expected = List.of(job);
        when(jobRepository.findAll()).thenReturn(List.of(job));
        List<JobServiceModel> actual = jobService.findAll();
        Assert.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assert.assertEquals(expected.get(0).getJobLevel().getJobLevel(), actual.get(0).getJobLevel());
        Assert.assertEquals(expected.get(0).getJobType().getJobType(), actual.get(0).getJobType());


    }
}
