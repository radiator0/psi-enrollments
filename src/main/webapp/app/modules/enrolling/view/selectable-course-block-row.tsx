import React, { Component } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import CheckIcon from '@material-ui/icons/Check';
import ClearIcon from '@material-ui/icons/Clear';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import Collapse from '@material-ui/core/Collapse';
import TogetherCourseBlockRow from './together-course-block-row';
import SelectableCourseBlockDetails from '../../../shared/model/domain/dto/selectable-course-block-details';
import CourseDetails from 'app/shared/model/domain/dto/course-details';

interface ISelectableCourseBlockRowProps {
  selectableCourseBlock: SelectableCourseBlockDetails;
  onSelected: (course: CourseDetails) => void;
}

interface ISelectableCourseBlockRowState {
  isOpen: boolean;
}

class SelectableCourseBlockRow extends Component<ISelectableCourseBlockRowProps, ISelectableCourseBlockRowState> {
  constructor(props: ISelectableCourseBlockRowProps) {
    super(props);

    this.state = {
      isOpen: false,
    };
  }

  setOpen(isOpen: boolean) {
    this.setState({ isOpen });
  }

  isEnrolledInAll() {
    return this.props.selectableCourseBlock.togetherBlocks.some(tb => tb.courses.every(c => c.enrolled));
  }

  rendedExpandableItem() {
    const { selectableCourseBlock } = this.props;
    const { isOpen } = this.state;

    return (
      <ListItem>
        <ListItemAvatar>
          <Avatar>{this.isEnrolledInAll() ? <CheckIcon /> : <ClearIcon />}</Avatar>
        </ListItemAvatar>
        <ListItemText primary={selectableCourseBlock.name} secondary={'Blok wybieralny'} />
        <ListItemSecondaryAction>
          <IconButton aria-label="expand row" size="small" onClick={() => this.setOpen(!isOpen)}>
            {isOpen ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </ListItemSecondaryAction>
      </ListItem>
    );
  }

  renderEmbeddedRows() {
    const { selectableCourseBlock, onSelected } = this.props;
    return (
      <>
        {selectableCourseBlock.togetherBlocks.map(x => (
          <TogetherCourseBlockRow key={x.id} togetherCourseBlock={x} onSelected={onSelected} />
        ))}
      </>
    );
  }

  shouldExpand() {
    return this.props.selectableCourseBlock.togetherBlocks.length > 1;
  }

  render() {
    const { isOpen } = this.state;

    return (
      <React.Fragment>
        {this.shouldExpand() ? (
          <>
            {this.rendedExpandableItem()}
            <Collapse in={isOpen} timeout="auto" unmountOnExit style={{ paddingLeft: 20 }}>
              {this.renderEmbeddedRows()}
            </Collapse>
          </>
        ) : (
          this.renderEmbeddedRows()
        )}
      </React.Fragment>
    );
  }
}

export default SelectableCourseBlockRow;
