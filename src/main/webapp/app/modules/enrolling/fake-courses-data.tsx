import SelectableCourseBlockDetails from '../../shared/model/domain/dto/selectable-course-block-details';
import CourseDetails from '../../shared/model/domain/dto/course-details';
import CourseUnitDetails from '../../shared/model/domain/dto/course-unit-details';
import { ClassType } from '../../shared/model/enumerations/class-type.model';
import CoursesData from './courses-data';

const data = [
    new SelectableCourseBlockDetails(null, null, [
        new CourseUnitDetails(null, null, null, null, null, [
            new CourseDetails(1, 'XD-666', 'Course 1 of block 1', 'C 1 of B 1', 1, ClassType.Laboratory, [], false),
            new CourseDetails(2, 'XD-6667', 'Course 2 of block 1', 'C 2 of B 1', 3, ClassType.Project, [], false)
        ])
    ]),

    new SelectableCourseBlockDetails(null, null, [
        new CourseUnitDetails(101, false, true, 'GK-101', 8, [
            new CourseDetails(101, 'GKE-1','Course 1 of block 2', 'C 1 of B 2', null, ClassType.Seminar, [], false),
            new CourseDetails(102, 'GKE-2','Course 2 of block 2', 'C 2 of B 2', null, ClassType.Lecture, [], false)
        ])
    ]),

    new SelectableCourseBlockDetails(3, 'Course block 3', [
        new CourseUnitDetails(201, true, false, 'PT-201', null, [
            new CourseDetails(201, 'PTE-01', 'Course 1 of block 3', 'C 1 of B 3', 1, ClassType.Lecture, [], false),
            new CourseDetails(202, 'PTE-02', 'Course 2 of block 3', 'C 2 of B 3', 3, ClassType.Laboratory, [], false)
    ]),
        new CourseUnitDetails(202, true, false, 'PT-202', null, [
            new CourseDetails(211, 'PTE-11', 'Course 1 of block 3', 'C 1 of B 3', 1, ClassType.Lecture, [], false),
            new CourseDetails(212, 'PTE-12', 'Course 2 of block 3', 'C 2 of B 3', 3, ClassType.Laboratory, [], false)
        ])
    ]),

    new SelectableCourseBlockDetails(null, null, [
        new CourseUnitDetails(null, null, null, null, null, [
            new CourseDetails(301, 'XD-2137', 'Course 1 of block 4', 'C 1 of B 4', 2, ClassType.Exercises, [], true)
        ])
    ])
];

export default data.map(d => new CoursesData(d));