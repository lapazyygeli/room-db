# 📚 RoomDb

RoomDb is an Android application designed to help users manage their reading lists effectively. It allows users to keep track of books they plan to read, set goals, and sort books based on various criteria.

---

## 🚀 How to Run the Program

### Prerequisites

- **Android Studio**
- **Java 8 or later** (JDK installed)
- An Android device or emulator running **Android 8.1 (API level 27)** or higher

### Installation Steps

1. **Clone the Repository**

   ```bash
   git clone <repository-url>
   cd room-db
   ```

2. **Open the Project**

   - Launch Android Studio.
   - Click **File > Open** and select the `room-db` directory.

3. **Sync Gradle Files**

   - Android Studio will automatically sync Gradle dependencies. Ensure you’re connected to the internet.

4. **Run the Application**

   - Connect an Android device or start an emulator.
   - Click the **Run** button in Android Studio (`Shift + F10`).

---

## 📚 About

RoomDb is a feature-rich app for managing a personalized to-read list. Users can:

- Add books by specifying the title, edition, and target completion date.
- Optionally assign a number to books (useful for tracking numbered series like comics).
- Mark books as read/unread.
- Sort books by name or number.
- View and edit details of added books.

### Key Features:

- **Image Support**: Attach photos for books, which can be viewed in larger detail by clicking on thumbnails.
- **Localization**: The app supports Finnish and English, automatically selecting the device's configured language.
- **Room Database**: Ensures data persistence even if the app is closed or restarted.
- **Confirmation for Deletions**: Prompts users to confirm before deleting books.
- **Rotational Support**: Maintains functionality and state when the device is rotated.

---

## ⚙️ Input Requirements and Limitations

- **Mandatory Fields**: Name, edition, and date are required to add a book.
- **Optional Fields**: The book number is optional (defaults to 0 if not provided).
- **Validation**:
  - If mandatory fields are incomplete, input fields are cleared.
  - Names can include numbers, but overall input length is limited.
- **Photos**: Users can’t remove a photo individually. Instead, delete the entire entry and re-add it with a new photo.

---

## 🌟 Features for Future Implementation

- Filter books by name or status (read/unread).
- Additional sorting criteria based on checked status.
- Refined UI
