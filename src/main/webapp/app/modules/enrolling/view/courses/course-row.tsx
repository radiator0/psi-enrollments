import React, { Component } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import CheckIcon from '@material-ui/icons/Check';
import ClearIcon from '@material-ui/icons/Clear';
import KeyboardArrowRightIcon from '@material-ui/icons/KeyboardArrowRight';
import CourseDetails from '../../../../shared/model/domain/dto/course-details';
import log from 'app/config/log';
import { CheckedAvatar, UnCheckedAvatar } from '../../custom-avatars';

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
        log.info(course);
        return (
            <ListItem className="list-item" onClick={() => this.onSelected()}>
                <ListItemAvatar>
                        {course.studentEnrolled ? <CheckedAvatar /> : <UnCheckedAvatar />}
                </ListItemAvatar>
                <ListItemText
                    primary={course.shortName || course.name}
                    secondary={course.form}
                />
            </ListItem>
        );
    }
}

export default CourseRow;