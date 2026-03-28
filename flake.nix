{
  description = "Replant Swamp Trees - Fabric mod development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = with pkgs; [
            pkgs.openjdk25

            just

            libGL
            libglvnd
            libx11
            libxcursor
            libxrandr
            libxi
            libxxf86vm
            glfw
          ];

          shellHook = ''
            export JAVA_HOME="${pkgs.openjdk25}"
            export PATH="$PWD/gradle/bin:$PATH"
            export LD_LIBRARY_PATH="${pkgs.lib.makeLibraryPath [
              pkgs.libGL
              pkgs.libglvnd
              pkgs.libx11
              pkgs.libxcursor
              pkgs.libxrandr
              pkgs.libxi
              pkgs.libxxf86vm
              pkgs.glfw
            ]}:$LD_LIBRARY_PATH"
            echo "Replant Swamp Trees dev environment"
            echo "Java: $(java -version 2>&1 | head -1)"
          '';
        };
      }
    );
}
