package com.softuni.service.impl;

import com.softuni.exception.JobNotFoundException;
import com.softuni.model.entity.Job;
import com.softuni.model.entity.Vehicle;
import com.softuni.model.service.JobServiceModel;
import com.softuni.model.service.UserServiceModel;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.repository.JobRepository;
import com.softuni.service.JobService;
import com.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.dnd.InvalidDnDOperationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.softuni.Constants.Constants.ACCESS_DENIED;
import static com.softuni.Constants.Constants.JOB_NOT_FOUND;

@Service
public class JobServiceImpl implements JobService {

    private final ModelMapper modelMapper;
    private final JobRepository jobRepository;
    private final UserService userService;

    public JobServiceImpl(ModelMapper modelMapper, JobRepository jobRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.jobRepository = jobRepository;
        this.userService = userService;
    }

    @Override
    public void addJob(JobServiceModel jobServiceModel, String user) {
        jobServiceModel.setActive(true);
        jobServiceModel.setAddedOn(LocalDate.now());
        jobServiceModel.setExpireOn(jobServiceModel.getAddedOn().plus(10, ChronoUnit.DAYS));
        jobServiceModel.setUser(this.userService.findByUsername(user));
        jobServiceModel.setUsername(user);
        Job job = this.modelMapper.map(jobServiceModel, Job.class);
        this.jobRepository.saveAndFlush(job);
    }

    @Override
    public List<JobServiceModel> findAll() {
        return this.jobRepository.findAll().stream().map(job -> this.modelMapper.map(job, JobServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public JobServiceModel findById(String id, String cyrillic) {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND));
        JobServiceModel jobServiceModel = this.modelMapper.map(job, JobServiceModel.class);
        if (!cyrillic.equals("yes")) {
            jobServiceModel.setRegion(job.getRegion().toString());
            jobServiceModel.setJobType(job.getJobType().toString());
            jobServiceModel.setJobLevel(job.getJobLevel().toString());
        }
        return jobServiceModel;
    }

    @Override
    public void deleteById(String id) {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (job.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
            this.jobRepository.deleteById(id);
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }


    @Override
    public void editJob(JobServiceModel jobServiceModel, String id) {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (job.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
            jobServiceModel.setAddedOn(job.getAddedOn());
            jobServiceModel.setUsername(job.getUsername());
            jobServiceModel.setExpireOn(job.getExpireOn());
            jobServiceModel.setActive(true);
            jobServiceModel.setUser(this.modelMapper.map(job.getUser(), UserServiceModel.class));
            this.jobRepository.saveAndFlush(this.modelMapper.map(jobServiceModel, Job.class));
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }

    }

    @Override
    public List<JobServiceModel> findAllJobsForUsername(String username) {
        List<JobServiceModel> jobs = this.jobRepository.findAllByUsername(username).stream()
                .map(job -> this.modelMapper.map(job, JobServiceModel.class)).collect(Collectors.toList());
        return jobs;
    }

    @Override
    public void renew(String id) {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND));
        JobServiceModel jobServiceModel = this.modelMapper.map(job, JobServiceModel.class);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (jobServiceModel.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            System.out.println();

            if (LocalDate.now().compareTo(jobServiceModel.getExpireOn()) > 0) {
                System.out.println();
                jobServiceModel.setExpireOn(jobServiceModel.getExpireOn().plus(10, ChronoUnit.DAYS));
                jobServiceModel.setActive(true);
                jobServiceModel.setJobLevel(job.getJobLevel().name());
                jobServiceModel.setJobType(job.getJobType().name());
                jobServiceModel.setRegion(job.getRegion().name());
                jobServiceModel.setUsername(job.getUsername());
                this.jobRepository.saveAndFlush(this.modelMapper.map(jobServiceModel, Job.class));
            }
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    @Override
    public void saveAll(List<JobServiceModel> jobs) {
        List<Job> toJob = jobs.stream().map(job -> this.modelMapper.map(job, Job.class)).collect(Collectors.toList());
        this.jobRepository.saveAll(toJob);
    }

}
