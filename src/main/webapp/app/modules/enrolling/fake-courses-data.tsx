import SelectableCourseBlockDetails from '../../shared/model/domain/dto/selectable-course-block-details';
import CourseDetails from '../../shared/model/domain/dto/course-details';
import TogetherCourseBlockDetails from '../../shared/model/domain/dto/together-course-block-details';

const data = [
    new SelectableCourseBlockDetails(1, 'Course block 1', [
        new TogetherCourseBlockDetails(1, 'Together block 1 of block 1', [
            new CourseDetails(1, 'Course 1 of block 1', false),
            new CourseDetails(2, 'Course 2 of block 1', false)
        ])
    ]),

    new SelectableCourseBlockDetails(2, 'Course block 2', [
        new TogetherCourseBlockDetails(101, 'Together block 1 of block 2', [
            new CourseDetails(101, 'Course 1 of block 2', true),
            new CourseDetails(102, 'Course 2 of block 2', true)
        ])
    ]),

    new SelectableCourseBlockDetails(3, 'Course block 3', [
        new TogetherCourseBlockDetails(201, 'Together block 1 of block 3', [
            new CourseDetails(201, 'Course 1 of block 3', false)
    ]),
        new TogetherCourseBlockDetails(202, 'Together block 2 of block 3', [
            new CourseDetails(202, 'Course 1 of block 3', false),
            new CourseDetails(203, 'Course 2 of block 3', false)
        ])
    ]),

    new SelectableCourseBlockDetails(4, 'Course block 4', [
        new TogetherCourseBlockDetails(301, 'Together block 1 of block 4', [
            new CourseDetails(1, 'Course 1 of block 4', true)
        ])
    ]),
];

export default data;