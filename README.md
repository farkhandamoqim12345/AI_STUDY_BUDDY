"# AI_STUDY_BUDDY" 
# AI Study Buddy - Offline Learning Assistant

AI Study Buddy ek Android application hai jo students ko core computer science subjects parhne aur samajhne mein madad karti hai. Ye app 100% offline kaam karti hai aur complex queries ko process karne ke liye Machine Learning ka istemal karti hai.

## 🚀 Key Features
- **Offline Knowledge Base:** 700+ technical records ka dataset jo bina internet ke chalta hai.
- **Smart Search:** TensorFlow Lite (TFLite) integration ke zariye user ki queries ko analyze kiya jata hai.
- **Multi-Subject Support:** Specially designed for:
    - Artificial Intelligence (AI)
    - Machine Learning (ML)
    - Database Systems (DBMS)
    - Android Development
- **High Performance:** Data retrieval ke liye HashMap ka istemal kiya gaya hai (O(1) complexity).

## 🛠️ Tech Stack
- **Language:** Java
- **Framework:** Android SDK
- **ML Engine:** TensorFlow Lite (.tflite model)
- **Data Storage:** Local CSV (Parsed via BufferedReader)
- **UI Design:** XML with Material Design components

## 📂 Project Structure
- `assets/`: Ismein `StudyBuddy_Master_Dataset.csv` aur `1.tflite` model file maujood hai.
- `java/`: Ismein backend logic, CSV parser, aur HashMap management ka code hai.
- `res/layout/`: App ka user interface aur dashboard design.

## 📖 How to Use
1. App open karein aur Dashboard se apna subject select karein.
2. Chat interface mein koi bhi technical term likhein (e.g., "Neural Network" ya "SQL").
3. App foran uska definition aur explanation display kar degi.

## 🧠 Intelligence Layer
Ye app simple keyword matching nahi karti balki TFLite model use karti hai taake future mein user ke intent ko mazeed behtar tareeqe se samjha ja sakay.