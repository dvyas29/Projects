import React, { useRef, useState } from 'react'
import { Container, Form, Button, Card, Alert } from 'react-bootstrap'
import { useAuth } from "../contexts/AuthContext"
import { Link, useHistory } from "react-router-dom"
import axios from "axios"

export default function Signup() {
    const nameRef = useRef();
    const emailRef = useRef();
    const passwordRef = useRef();
    const passwordConfirmRef = useRef();
    const questionRef = useRef();
    const answerRef = useRef();
    const typeRef = useRef();
    const { signup } = useAuth();
    const [error, setError] = useState("");
    const history = useHistory();
    const question = useState({
        question1: process.env.REACT_APP_QUESTION_1,
        question2: process.env.REACT_APP_QUESTION_2,
        question3: process.env.REACT_APP_QUESTION_3,
        question4: process.env.REACT_APP_QUESTION_4,
    })
    var data = {}

    async function handleSubmit(e) {
        e.preventDefault()
        if (passwordRef.current.value !== passwordConfirmRef.current.value) {
            return setError("Password do not match")
        }
        if (isNaN(questionRef.current.value)) {
            return setError("Security Question not Selected")
        }
        if (isNaN(typeRef.current.value)) {
            return setError("User Type not Selected")
        }
        try {
            setError("")
            await signup(emailRef.current.value, passwordRef.current.value).then(response => {
                data = {
                    email_id: emailRef.current.value,
                    name: nameRef.current.value,
                    question: questionRef.current.value,
                    answer: answerRef.current.value,
                    type: typeRef.current.value
                }
                console.log(data)
                createSubcription()
            })
        } catch (error) {
            setError(error.message)
        }
    }

    async function createSubcription() {
        await axios.put("https://us-central1-jeyanth-project.cloudfunctions.net/creation", data)
            .then((response) => {
                axios
                    .put("https://9x3jukzrb8.execute-api.us-east-1.amazonaws.com/items", data)
                    .then((response) => {
                        history.push("/")
                    }).catch(function (error) {
                        console.log(error);
                    });
            }).catch(function (error) {
                console.log(error);
            });
    }

    return (
        <Container className="d-flex align-items-center justify-content-center" style={{ minHeight: "100vh" }}>
            <div className="w-100" style={{ maxWidth: "400px" }}>
                <Card>
                    <Card.Body>
                        <h2 className="text-center mb-4">Sign Up</h2>
                        {error && <Alert variant="danger">{error}</Alert>}
                        <Form onSubmit={handleSubmit}>
                            <Form.Group id="user_type">
                                <Form.Label>User Type</Form.Label>
                                <Form.Select ref={typeRef} required >
                                    <option>Select a User Type</option>
                                    <option value="1">User</option>
                                    <option value="2">Representative</option>
                                    <option value="3">Manager</option>
                                </Form.Select>
                            </Form.Group>
                            <Form.Group id="name">
                                <Form.Label>Name</Form.Label>
                                <Form.Control type="text" ref={nameRef} required />
                            </Form.Group>
                            <Form.Group id="email">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" ref={emailRef} required />
                            </Form.Group>
                            <Form.Group id="password">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" ref={passwordRef} required />
                            </Form.Group>
                            <Form.Group id="confirm-password">
                                <Form.Label>Password Confirmation</Form.Label>
                                <Form.Control type="password" ref={passwordConfirmRef} required />
                            </Form.Group>
                            <Form.Group id="question">
                                <Form.Label>Security Question</Form.Label>
                                <Form.Select ref={questionRef} required >
                                    <option>Select a security Question</option>
                                    <option value="1">{question[0].question1}</option>
                                    <option value="2">{question[0].question2}</option>
                                    <option value="3">{question[0].question3}</option>
                                    <option value="4">{question[0].question4}</option>
                                </Form.Select>
                            </Form.Group>
                            <Form.Group id="answer">
                                <Form.Label>Answer</Form.Label>
                                <Form.Control type="text" ref={answerRef} required />
                            </Form.Group>
                            <Button className="w-100" type="submit">Sign Up</Button>
                        </Form>
                    </Card.Body>
                    <div className="w-100 text-center mt-2">
                        Aready Have an account? <Link to="/" style={{ textDecoration: "none" }}>Log In</Link>
                    </div>
                </Card>
            </div>
        </Container>
    )
}
