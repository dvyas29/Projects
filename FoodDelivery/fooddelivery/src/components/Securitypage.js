import axios from 'axios'
import React, { useState, useRef, useEffect } from 'react'
import { Container, Form, Button, Card, Alert } from 'react-bootstrap'
import { useHistory, useLocation } from "react-router-dom"

export default function Securitypage(props) {
    const questionList = [
        process.env.REACT_APP_QUESTION_1
        ,
        process.env.REACT_APP_QUESTION_2
        ,
        process.env.REACT_APP_QUESTION_3
        ,
        process.env.REACT_APP_QUESTION_4
    ]

    const history = useHistory();
    const [question, setQuestion] = useState("")
    const [name, setName] = useState("")
    const [answer, setAnswer] = useState("")
    const [error, setError] = useState("")
    const [type, setType] = useState("")
    const questionRef = useRef()
    const answerRef = useRef()
    const location = useLocation();
    var userData = {}

    useEffect(() => {
        const fetchData = async () => {
            console.log(location)
            if (location.state === undefined) {
                alert("Please login")
                history.push("/")
            } else {
                await axios.get("https://9x3jukzrb8.execute-api.us-east-1.amazonaws.com/token/" + location.state.email).then((responseOne) => {
                    // if (responseOne.data.Item.token !== localStorage.token) {
                    //     alert("Please login")
                    //     history.push("/homepage")
                    // } else {
                    axios.get("https://9x3jukzrb8.execute-api.us-east-1.amazonaws.com/items/" + location.state.email)
                        .then((response) => {
                            setQuestion(questionList[response.data.Item.question - 1]);
                            setAnswer(response.data.Item.answer)
                            setName(response.data.Item.name)
                            setType(response.data.Item.type)
                        }).catch(function (error) {
                            console.log(error);
                        });
                    // }
                }).catch(function (error) {
                    console.log(error);
                });
            }
        }
        fetchData()
    }, []);

    async function saveToken() {
        userData = {
            email_id: location.state.email,
            token: location.state.token
        }
        await axios.put("https://9x3jukzrb8.execute-api.us-east-1.amazonaws.com/token", userData)
            .then((response) => {
                console.log(response)
            }).catch(function (error) {
                console.log(error);
            });
    }
    async function handleSubmit(e) {
        e.preventDefault()
        try {
            setError("")
            console.log(answerRef.current.value)
            console.log(answer)
            if (answerRef.current.value.toLowerCase() !== answer.toLowerCase()) {
                return setError("Wrong Answer! Pls Try again")
            }
            saveToken().then(function () {

                localStorage.setItem("userData", JSON.stringify(userData))
                localStorage.setItem("name", name)
                localStorage.setItem("type", type)
                console.log(localStorage)
                history.push("/homepage")
            })
        } catch (error) {
            setError(error.message)
        }
    }
    return (
        <Container className="d-flex align-items-center justify-content-center" style={{ minHeight: "100vh" }}>
            <div className="w-100" style={{ maxWidth: "400px" }}>
                <Card>
                    <Card.Body>
                        <h2 className="text-center mb-4">Security Login</h2>
                        {error && <Alert variant="danger">{error}</Alert>}
                        <Form onSubmit={handleSubmit}>
                            <Form.Group id="question">
                                <Form.Label>Security Question</Form.Label>
                                <Form.Control type="text" ref={questionRef} value={question} readOnly />
                            </Form.Group>
                            <Form.Group id="answer">
                                <Form.Label>Answer</Form.Label>
                                <Form.Control type="text" ref={answerRef} required />
                            </Form.Group>
                            <Button className="w-100" type="submit">Submit</Button>
                        </Form>
                    </Card.Body>
                </Card>
            </div></Container>
    )
}
