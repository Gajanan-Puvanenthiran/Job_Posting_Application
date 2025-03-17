package com.gajanan.Job.Posting.Application.service.impl;

import com.gajanan.Job.Posting.Application.exception.JobNotFoundException;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    //to avoid real database interactions
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    private Job job;
    private RequestDTO requestDTO;

    //this will run before each tests
    @BeforeEach
    void setUp() {
        job = new Job();
        job.setId(1L);
        job.setTitle("Software Engineer");
        job.setCompany("Dilimatrix");
        job.setDescription("Develop software");
        job.setLocation("Sri Lanka");
        job.setSalary(95000.0);

        requestDTO = new RequestDTO();
        requestDTO.setTitle("Software Engineer");
        requestDTO.setCompany("Dilimatrix");
        requestDTO.setDescription("Develop software");
        requestDTO.setLocation("Sri Lanka");
        requestDTO.setSalary(95000.0);
    }

    @Test
    void createJob_ShouldReturnSavedJob() {
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        Job savedJob=jobService.createJob(requestDTO);
        assertNotNull(savedJob);
        assertEquals(job.getTitle(), savedJob.getTitle());
        verify(jobRepository).save(any(Job.class));


    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs() {
        //when
        jobService.getAllJobs();
        //then
        verify(jobRepository).findAll();
    }

    @Test
    void getJob_ShouldReturnJob_WhenJobExists() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Job foundJob = jobService.getJob(1L);
        assertNotNull(foundJob);
        assertEquals(job.getId(), foundJob.getId());
        verify(jobRepository).findById(1L);


    }

    @Test
    void getJob_ShouldThrowException_WhenJobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(JobNotFoundException.class, () -> jobService.getJob(1L));
        verify(jobRepository).findById(1L);
    }

    @Test
    void updateJob_ShouldReturnUpdatedJob() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        Job updatedJob = jobService.updateJob(1L, requestDTO);
        assertNotNull(updatedJob);
        assertEquals(job.getTitle(), updatedJob.getTitle());
        verify(jobRepository).save(any(Job.class));
    }

    @Test
    void deleteJob_ShouldRemoveJob_WhenJobExists() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        doNothing().when(jobRepository).delete(job);

        Job deletedJob = jobService.deleteJob(1L);
        assertNotNull(deletedJob);
        assertEquals(job.getId(), deletedJob.getId());
        verify(jobRepository).delete(job);
    }

    @Test
    void deleteJob_ShouldThrowException_WhenJobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(JobNotFoundException.class, () -> jobService.deleteJob(1L));
        verify(jobRepository).findById(1L);
    }
}