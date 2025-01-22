# ChickenPlanner: An Open Source FRC Trajectory Planner 🐔

<img src="src/main/resources/AppIcon.png" alt="ChickenPlanner Logo" width="300">

**ChickenPlanner** is a powerful, user-friendly trajectory planner designed for FIRST Robotics Competition (FRC) teams. Built to make trajectory generation straightforward with bezier curves, and help plan autos.

---

## 🚀 **Open Source Project**

This project is proudly open-source! We welcome contributions from the community to improve ChickenPlanner and help it become the ultimate tool for FRC teams. Found a bug? Got an idea for a feature?  

👉 **[Join our Discord Community!](https://discord.gg/Gg8XQRPKdx)**  
Help us make ChickenPlanner even better by reporting bugs, discussing new features, or just hanging out with the developers.

---

## 📥 Installation

## Installation

### Releases
To download the latest build navigate yourself to the [Releases](https://github.com/team3082/ChickenPlanner/releases) to download platform builds. [^1]

### Unstable builds
For unstable builds navigate to the [Actions](https://github.com/team3082/ChickenPlanner/actions) and download the JAR artifact from a commit of your choice.    [^1]

---

## 🚧 Building

1. **Clone the Repository**
    ```bash
    git clone git@github.com:team3082/ChickenPlanner.git && cd ChickenPlanner
    ```
2. **Build the Project**
    ```bash
    mvn -Drevision=<build version> package
    ```
   
3. **Run the Application**
    ```bash
    java -jar target/ChickenPlanner-<build version>.jar
    ```

## 🛠️ Usage

*Coming Soon:* A detailed guide on how to create, customize, and execute trajectories using ChickenPlanner and a more easier user experience with no need for maven commands
- **Run the Application from source**
    ```bash
    mvn javafx:run
    ```
---

## 🧑‍💻 Codebase Structure

*Coming Soon:* An overview of ChickenPlanner's codebase, including its modular components and how everything fits together. Here's what to expect:  
- **Core Library**: The main trajectory generation algorithms. 
- **Robot Integration**: Examples and utilities to deploy trajectories on FRC robots.  
- **Testing and Simulation**: Frameworks to validate paths before deployment.

---

## 📝 Contributing

We love contributions! Whether it's fixing bugs, adding new features, improving documentation, or testing, there's a place for everyone to help. Here's how to get started:  
1. Fork the repository.  
2. Clone it to your machine.  
3. Make your changes on a feature branch.  
4. Submit a pull request.  

For detailed guidelines, check out our [CONTRIBUTING.md](CONTRIBUTING.md).  

---

## 🐛 Bug Reporting & Feedback

Encountered a problem? Have a suggestion? Let us know!  
- Report issues via [GitHub Issues](https://github.com/Team3082/ChickenPlanner/issues).  
- Join our [Discord Community](https://discord.gg/Gg8XQRPKdx) to chat with the team and other users.

---

## 📜 License

ChickenPlanner is licensed under the [MIT License](LICENSE). Feel free to use it in your projects while adhering to the license terms.

---

Join the journey and help make ChickenPlanner the best tool it can be! 🐔

[^1]: Jar files need to be run using a JRE with java 23.0.1 or higher using the command ``java -var <jar path>``