import React, { Component } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemText from '@material-ui/core/ListItemText';
import CourseDetails from '../../../../shared/model/domain/dto/course-details';
import { translate } from 'react-jhipster';
import { CheckedAvatar, UnCheckedAvatar } from '../../custom-avatars';
import log from 'app/config/log';

interface ICourseRowProps {
    course: CourseDetails,
    onSelected: (course: CourseDetails) => void
};

// eslint-disable-next-line @typescript-eslint/no-empty-interface
interface ICourseRowState {
};


class CourseRow extends Component<ICourseRowProps, ICourseRowState> {
    constructor(props: ICourseRowProps) {
        super(props);

        this.state = {
            isOpen: false
        };
    }

    onSelected() {
        const { onSelected, course } = this.props;
        onSelected(course);
    }

    render() {
        const { course } = this.props;
        return (
            <ListItem className="list-item" onClick={() => this.onSelected()}>
                <ListItemAvatar>
                    {course.studentEnrolled ? <CheckedAvatar /> : <UnCheckedAvatar />}
                </ListItemAvatar>
                <ListItemText
                    primary={course.shortName || course.name}
                    secondary={<span style={{ display: 'flex' }}>
                        <span style={{ width: '100%' }}>
                            {translate(`enrollmentsApp.ClassType.${course.form}`)}
                        </span>
                        <span style={{ width: '100%', marginRight: '30px', textAlign: 'right' }}>
                            {course.ects ? `${course.ects} ECTS` : ''}
                        </span>
                    </span>}
                />
            </ListItem>
        );
    }
}

export default CourseRow;