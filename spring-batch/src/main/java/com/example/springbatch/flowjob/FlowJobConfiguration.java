//package com.example.springbatch;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@RequiredArgsConstructor
//@Configuration
//public class FlowJobConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job batchJob() {
//        return this.jobBuilderFactory.get("batchJob")
//                .start(flow())
//                .next(step3())
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Flow flow() {
//        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
//
//        flowBuilder.start(step1())
//                .next(step2())
//                .end();
//
//        return flowBuilder.build();
//    }
//
//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println(">> step1 has executed");
//
//                        contribution.getStepExecution().setExitStatus(ExitStatus.FAILED);
//
//                        return RepeatStatus.FINISHED;
//                    }
//                }).build();
//    }
//
//    @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("step2")
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println(">> step2 has executed");
//                        return RepeatStatus.FINISHED;
//                    }
//                })
//                .build();
//    }
//
//    @Bean
//    public Step step3() {
//        return stepBuilderFactory.get("step3")
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println(">> step3 has executed");
//                        return RepeatStatus.FINISHED;
//                    }
//                })
//                .build();
//    }
//}
//
