import React, {Component, Fragment} from 'react';
import Dropzone, {useDropzone} from 'react-dropzone';
import Button from "react-bootstrap/Button";

class AddPhotos extends Component{


    constructor(props) {
        super(props);

        this.state = {
            files: [],
        };

        this.handleSave = this.handleSave.bind(this);
    }

    validateForm() {
        return this.state.files.length > 0;
    }

    handleSave = (e) =>
    {
        for (const [key, value] of Object.entries(this.state)) {
            // console.log(`There are ${key} ${value}`);
            this.props.setProps(key,value);
        }
    }

    onPreviewDrop = (files) => {
        this.setState({
            files: this.state.files.concat(files),
        });
    }


    onChoose = (e) => {
        this.setState({
            files: this.state.files.concat(e.target.files[0]),
        });
    }

    render() {

        const files = this.state.files.map(file => (
            <li key={file.path}>
                {file.path} - {file.size} bytes
            </li>
        ));

        return (
            <div className="app">
                <Dropzone onDrop={acceptedFiles => this.onPreviewDrop(acceptedFiles)}>
                    {({getRootProps, getInputProps}) => (
                        <section className="drag-container">
                            <div {...getRootProps({className: 'dropzone'})}>
                                <input {...getInputProps()} />
                                <p>Drag 'n' drop some files here, or click to select files</p>
                            </div>
                        </section>
                    )}

                </Dropzone>

                <aside>
                    <h4>Files</h4>
                    <ul>{files}</ul>
                </aside>

                <Button
                    className = "btn btn-primary"
                    block
                    disabled={!this.validateForm()}
                    type="button"
                    onClick={ (e) => this.handleSave(e) }
                >
                    Save
                </Button>

                {/*<button type="button" className="btn btn-primary" onClick={ (e) => this.handleSave(e)}>Save</button>*/}
            </div>
        );
    }

}

export default AddPhotos;
