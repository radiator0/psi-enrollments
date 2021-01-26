import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Translate } from 'react-jhipster';
import { TextField } from '@material-ui/core';

interface IModalOption {
    optionNameKey: string,
    onClick: (text: string | null) => void
}

interface IAlertDialogProps {
    title: string,
    content: string,
    options: Array<IModalOption>,
    textField: { placeholderText: string }
}

const renderOptions = (options: Array<IModalOption>, userText: string) => {
    return (
        <>
            {options.map(o =>
                <Button key={o.optionNameKey} onClick={() => o.onClick(userText)} color="primary">
                    <Translate contentKey={o.optionNameKey}>{o.optionNameKey}</Translate>
                </Button>)}
        </>
    );
}

const renderContent = (props: IAlertDialogProps, setUserText: (text: string) => void) => {
    return (<>
        {props.content ?
        <DialogContentText id="alert-dialog-description">
            {props.content}
        </DialogContentText> : <></>}

        {props.textField ?
        <TextField
            autoFocus
            variant="outlined"
            margin="dense"
            id="name"
            fullWidth
            multiline
            defaultValue={props.textField.placeholderText}
            rows={10}
            onChange={(e) => setUserText(e.target.value)}
        /> : <></>}
    </>);
}

export default function AlertDialog(props: IAlertDialogProps) {
    const [userText, setUserText] = useState(props.textField?.placeholderText);

    return (
        <div>
            <Dialog
                open={true}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{props.title}</DialogTitle>
                <DialogContent>
                    {renderContent(props, setUserText)}
                </DialogContent>
                <DialogActions>
                    {renderOptions(props.options, userText)}
                </DialogActions>
            </Dialog>
        </div>
    );
}
